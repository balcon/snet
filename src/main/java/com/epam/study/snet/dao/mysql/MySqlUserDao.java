package com.epam.study.snet.dao.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {
    private final DataSource dataSource;

    public MySqlUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(String firstName, String lastName, String username, String passHash) {
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.users (firstName, lastName, username, passHash) VALUES (?,?,?,?)");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, passHash);
            statement.execute();
            long userId = 0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                userId = generatedKeys.getLong(1);
            user = User.builder()
                    .id(userId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .passHash(passHash)
                    .build();
        } catch (SQLException e) {
            throw new DaoException("Can't create user", e);
        }
        return user;
    }

    @Override
    public List<User> getList() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT userId, firstName,lastName, username, passHash FROM snet.users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get userlist", e);
        }
        return users;
    }

    @Override
    public User getById(Long id) {
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT userId, firstName,lastName, username, passHash FROM snet.users WHERE userId=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get user", e);
        }
        return user;
    }

    @Override
    public User getByUsername(String username) {
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT userId, firstName,lastName, username, passHash FROM snet.users WHERE username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get user", e);
        }
        return user;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .id(resultSet.getLong("userId"))
                .firstName(resultSet.getString("firstName"))
                .lastName(resultSet.getString("lastName"))
                .username(resultSet.getString("username"))
                .passHash(resultSet.getString("passHash"))
                .build();
        return user;
    }
}
