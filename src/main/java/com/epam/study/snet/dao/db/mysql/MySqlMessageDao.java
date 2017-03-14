package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlMessageDao implements MessageDao {
    private final DataSource dataSource;

    public MySqlMessageDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Message create(Message message) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            LocalDateTime currentTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.messages (senderId, receiverId, messageBody,sendingTime) VALUES (?,?,?,?)");
            statement.setLong(1, message.getSender().getId());
            statement.setLong(2, message.getReceiver().getId());
            statement.setString(3, message.getBody());
            statement.setString(4, currentTime.toString());
            statement.execute();

            long messageId = 0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                messageId = generatedKeys.getLong(1);
            message.setId(messageId);
            message.setSendingTime(currentTime);
        } catch (SQLException e) {
            throw new DaoException("Can't create message", e);
        }
        return message;
        //todo: think about return, when exception happens
    }

    @Override
    public List<Message> getListOfLastMessages(User user) throws DaoException {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT messageId,senderId,receiverId,sendingTime,messageBody FROM snet.messages JOIN " +
                    "(SELECT LEAST(senderId,receiverId) u1, GREATEST (senderId,receiverId) u2, MAX(sendingTime) maxTime " +
                    "FROM snet.messages WHERE senderId=? OR receiverId=? GROUP BY u1,u2) lastMessage " +
                    "ON sendingTime=maxTime AND LEAST(senderId,receiverId)=u1 AND GREATEST (senderId,receiverId)=u2 " +
                    "ORDER BY sendingTime DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            statement.setLong(2, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(getMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get messages list", e);
        }
        return messages;

    }

    @Override
    public List<Message> getListBySenderAndReceiver(User sender, User receiver) throws DaoException {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT messageId, senderId, receiverId, messageBody, sendingTime FROM snet.messages " +
                            "WHERE (senderId=? AND receiverId=?) OR (senderId=? AND receiverId=?) " +
                            "ORDER BY sendingTime DESC");
            statement.setLong(1, sender.getId());
            statement.setLong(2, receiver.getId());
            statement.setLong(3, receiver.getId());
            statement.setLong(4, sender.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(getMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get messages list", e);
        }
        return messages;

    }

    private Message getMessageFromResultSet(ResultSet resultSet) throws DaoException, SQLException {
        //TODO: think! How make better
        long senderId = resultSet.getLong("senderId");
        User sender = new MySqlDaoFactory(dataSource).getUserDao().getById(senderId);
        //TODO: this to
        long receiverId = resultSet.getLong("receiverId");
        User reciever = new MySqlDaoFactory(dataSource).getUserDao().getById(receiverId);

        Date sendingDate = resultSet.getDate("sendingTime");
        Time sendingTime = resultSet.getTime("sendingTime");

        Message message = Message.builder()
                .id(resultSet.getLong("messageId"))
                .sender(sender)
                .receiver(reciever)
                .body(resultSet.getString("messageBody"))
                .sendingTime(LocalDateTime.of(sendingDate.toLocalDate(), sendingTime.toLocalTime()))
                .build();
        return message;
    }
}
