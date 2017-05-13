package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.Message;
import com.epam.study.snet.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlMessageDao implements MessageDao {
    private final DataSource dataSource;
    private final UserDao userDao;

    MySqlMessageDao(DataSource dataSource, UserDao userDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
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
    }

    //TODO heisenbag in tests
    @Override
    public List<Message> getListOfLatest(User user) throws DaoException {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM snet.messages JOIN " +
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
    public List<Message> getListBetweenUsers(User user1, User user2) throws DaoException {
        String queryString = "SELECT * FROM snet.messages " +
                "WHERE (senderId=? AND receiverId=?) OR (senderId=? AND receiverId=?) " +
                "ORDER BY sendingTime DESC";
        return executeGetList(queryString, user1.getId(), user2.getId(), user2.getId(), user1.getId());
    }

    @Override
    public List<Message> getListBetweenUsers(User user1, User user2, int skip, int limit) throws DaoException {
        String queryString = "SELECT * FROM snet.messages " +
                "WHERE (senderId=? AND receiverId=?) OR (senderId=? AND receiverId=?) " +
                "ORDER BY sendingTime DESC LIMIT ?,?";
        return executeGetList(queryString, user1.getId(), user2.getId(), user2.getId(), user1.getId(), skip, limit);
    }

    @Override
    public int getNumberUnread(User receiver) throws DaoException {
        String queryString = "SELECT COUNT(*) FROM snet.messages " +
                "WHERE receiverId=? AND unread=TRUE";
        return executeGetNumber(queryString, receiver);
    }

    @Override
    public int getNumberUnreadBetweenUsers(User sender, User receiver) throws DaoException {
        String queryString = "SELECT COUNT(*) FROM snet.messages " +
                "WHERE senderId=? AND receiverId=? AND unread=TRUE";
        return executeGetNumber(queryString, sender, receiver);
    }

    @Override
    public int getNumberBetweenUsers(User user1, User user2) throws DaoException {
        String queryString = "SELECT COUNT(*) FROM snet.messages " +
                "WHERE (senderId=? AND receiverId=?) OR (senderId=? AND receiverId=?)";
        return executeGetNumber(queryString, user1, user2, user2, user1);
    }

    @Override
    public void makeReadBetweenUsers(User sender, User receiver) throws DaoException {
        String queryString = "UPDATE snet.messages SET unread=FALSE " +
                "WHERE (senderId=? AND receiverId=?)";
        executeVoid(queryString, sender.getId(), receiver.getId());
    }

    @Override
    public void removeById(long messageId) throws DaoException {
        String queryString = "DELETE FROM snet.messages WHERE messageId=? AND unread=TRUE";
        executeVoid(queryString, messageId);
    }

    private List<Message> executeGetList(String queryString, long... param) throws DaoException {
        List<Message> messages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(queryString);
            for (int i = 0; i < param.length; i++) {
                statement.setLong(i + 1, param[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(getMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get messages list", e);
        }
        return messages;
    }

    private void executeVoid(String queryString, long... param) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    queryString);
            for (int i = 0; i < param.length; i++) {
                statement.setLong(i + 1, param[i]);
            }
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't execute query", e);
        }
    }

    private int executeGetNumber(String queryString, User... users) throws DaoException {
        int numberUnreadMessages = 0;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(queryString);
            for (int i = 0; i < users.length; i++) {
                statement.setLong(i + 1, users[i].getId());
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberUnreadMessages = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get number of messages", e);
        }
        return numberUnreadMessages;
    }

    private Message getMessageFromResultSet(ResultSet resultSet) throws DaoException, SQLException {
        long senderId = resultSet.getLong("senderId");
        User sender = userDao.getById(senderId);
        long receiverId = resultSet.getLong("receiverId");
        User reciever = userDao.getById(receiverId);

        Date sendingDate = resultSet.getDate("sendingTime");
        Time sendingTime = resultSet.getTime("sendingTime");

        return Message.builder()
                .id(resultSet.getLong("messageId"))
                .sender(sender)
                .receiver(reciever)
                .body(resultSet.getString("messageBody"))
                .sendingTime(LocalDateTime.of(sendingDate.toLocalDate(), sendingTime.toLocalTime()))
                .unread(resultSet.getBoolean("unread")).build();
    }
}
