package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.StatusMessageDao;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.Message;
import com.epam.study.snet.entity.StatusMessage;
import com.epam.study.snet.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

public class MySqlStatusMessageDao implements StatusMessageDao {

    private DataSource dataSource;
    private UserDao userDao;

    public MySqlStatusMessageDao(DataSource dataSource, UserDao userDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
    }

    @Override
    public StatusMessage create(User user, String body) throws DaoException {
        StatusMessage message;
        try (Connection connection = dataSource.getConnection()) {
            LocalDateTime currentTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.status_messages (authorId, messageBody,sendingTime) VALUES (?,?,?)");
            statement.setLong(1, user.getId());
            statement.setString(2, body);
            statement.setString(3, currentTime.toString());
            statement.execute();

            long messageId = 0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                messageId = generatedKeys.getLong(1);
            message = StatusMessage.builder()
                    .id(messageId)
                    .author(user)
                    .body(body)
                    .sendingTime(currentTime).build();
        } catch (SQLException e) {
            throw new DaoException("Can't create status message", e);
        }
        return message;
    }

    @Override
    public StatusMessage getByUser(User user) throws DaoException {
        StatusMessage message=null;
        try (Connection connection = dataSource.getConnection()) {
            LocalDateTime currentTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.status_messages WHERE authorId=? ORDER BY sendingTime DESC");
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                long authorId = resultSet.getLong("authorId");
                User author = userDao.getById(authorId);

                Date sendingDate = resultSet.getDate("sendingTime");
                Time sendingTime = resultSet.getTime("sendingTime");

                message = StatusMessage.builder()
                        .id(resultSet.getLong("messageId"))
                        .author(author)
                        .body(resultSet.getString("messageBody"))
                        .sendingTime(LocalDateTime.of(sendingDate.toLocalDate(), sendingTime.toLocalTime()))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get status message", e);
        }
        return message;
    }
}
