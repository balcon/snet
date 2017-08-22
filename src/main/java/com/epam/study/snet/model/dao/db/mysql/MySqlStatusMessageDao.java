package com.epam.study.snet.model.dao.db.mysql;

import com.epam.study.snet.model.dao.DaoException;
import com.epam.study.snet.model.dao.StatusMessageDao;
import com.epam.study.snet.model.entity.StatusMessage;
import com.epam.study.snet.model.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;

public class MySqlStatusMessageDao implements StatusMessageDao {

    private DataSource dataSource;

    MySqlStatusMessageDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public StatusMessage create(User user, String body) throws DaoException {
        StatusMessage message;
        try (Connection connection = dataSource.getConnection()) {
            LocalDateTime currentTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.status_messages (authorId, messageBody, sendingTime) VALUES (?,?,?)");
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
                    .body(body)
                    .sendingTime(currentTime).build();
        } catch (SQLException e) {
            throw new DaoException("Can't create status message", e);
        }
        return message;
    }

    @Override
    public StatusMessage getByUserId(long userId) throws DaoException {
        StatusMessage message = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.status_messages WHERE sendingTime=" +
                            "(SELECT MAX(sendingTime) FROM snet.status_messages WHERE authorId=?) AND authorId=?");
            statement.setLong(1, userId);
            statement.setLong(2, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Date sendingDate = resultSet.getDate("sendingTime");
                Time sendingTime = resultSet.getTime("sendingTime");

                message = StatusMessage.builder()
                        .id(resultSet.getLong("messageId"))
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
