package com.epam.study.snet.dao.MySqlH2;

import com.epam.study.snet.Static;
import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlH2MessageDao implements MessageDao {
    private final DataSource dataSource;

    public MySqlH2MessageDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Message createMessage(User sender, User reciever, String body) {
        Message message = null;
        try (Connection connection = dataSource.getConnection()) {
            LocalDateTime currentTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.messages (senderId, receiverId, messageBody,sendingTime) VALUES (?,?,?,?)");
            statement.setLong(1, sender.getId());
            statement.setLong(2, reciever.getId());
            statement.setString(3, body);
            statement.setString(4, currentTime.toString());
            statement.execute();
            long messageId = 0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                messageId = generatedKeys.getLong(1);
            message = message.builder()
                    .id(messageId)
                    .sender(sender)
                    .receiver(reciever)
                    .body(body)
                    .sendingTime(LocalDateTime.now())
                    .build();
        } catch (SQLException e) {
            throw new DaoException("Can't create message", e);
        }
        return message;
    }

    @Override
    public List<Message> getListOfLastMessages(User user) {
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
    public List<Message> getListBySenderAndReceiver(User sender, User receiver) {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT messageId, senderId, receiverId, messageBody, sendingTime from snet.messages " +
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

    private Message getMessageFromResultSet(ResultSet resultSet) throws SQLException {
        long senderId = resultSet.getLong("senderId");
        User sender = Static.daoFactory.getUserDao().getById(senderId);
//TODO: do something H2Dao MySqlDao
        long recieverId = resultSet.getLong("receiverId");
        User reciever = Static.daoFactory.getUserDao().getById(recieverId);

        Date sendingDate=resultSet.getDate("sendingTime");
        Time sendingTime=resultSet.getTime("sendingTime");

        Message message = Message.builder()
                .id(resultSet.getLong("messageId"))
                .sender(sender)
                .receiver(reciever)
                .body(resultSet.getString("messageBody"))
                .sendingTime(LocalDateTime.of(sendingDate.toLocalDate(),sendingTime.toLocalTime()))
                .build();
        return message;
    }
}
