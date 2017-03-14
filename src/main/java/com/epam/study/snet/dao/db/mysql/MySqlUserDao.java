package com.epam.study.snet.dao.db.mysql;

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

    public User create(User user) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.users (username, passHash, firstName, lastName) VALUES (?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.execute();
            long userId = 0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next())
                userId = generatedKeys.getLong(1);
            user.setId(userId);
        } catch (SQLException e) {
            throw new DaoException("Can't create user", e);
        }
        return user;
    }

    @Override
    public List<User> getList() throws DaoException {
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
    public User getById(Long id) throws DaoException {
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
    public User getByUsername(String username) throws DaoException {
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
                .username(resultSet.getString("username"))
                .password(resultSet.getString("passHash"))
                .firstName(resultSet.getString("firstName"))
                .lastName(resultSet.getString("lastName"))
                .build();
        return user;
    }
}
