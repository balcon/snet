package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.model.Image;
import com.epam.study.snet.entity.User;

import javax.sql.DataSource;
import java.sql.*;
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
                    "INSERT INTO snet.users (username, passHash, firstName, lastName, birthday, gender)" +
                            " VALUES (?,?,?,?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getBirthday().toString());
            statement.setString(6, user.getGender().toString());
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
                    "SELECT * FROM snet.users WHERE deleted=FALSE");
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
    public List<User> getList(User excludedUser) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.users WHERE userId!=? AND deleted=FALSE ORDER BY lastName,firstName");
            statement.setLong(1, excludedUser.getId());
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
    public List<User> getList(User excludedUser, long skip, int limit) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.users WHERE userId!=? AND deleted=FALSE ORDER BY lastName,firstName LIMIT ?,?");
            statement.setLong(1, excludedUser.getId());
            statement.setLong(2, skip);
            statement.setLong(3, limit);
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
                    "SELECT * FROM snet.users WHERE userId=?");
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
                    "SELECT * FROM snet.users WHERE username=?");
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

    @Override
    public long getNumber() throws DaoException {
        int numberUsers = 0;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM snet.users WHERE deleted=FALSE");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numberUsers = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get numberm users", e);
        }
        return numberUsers;
    }

    @Override
    //TODO what is it?
    public void update(User user) throws DaoException {
        long userId;
        long imageId;
        userId = user.getId();
        imageId = user.getPhoto().getId();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE snet.users SET imageId=? WHERE userId=?");
            statement.setLong(1, imageId);
            statement.setLong(2, userId);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    @Override
    public void updateById(Long id, User newUser) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE snet.users SET firstName=?,lastName=?,passHash=?,birthday=?,gender=?" +
                            " WHERE userId=?");
            statement.setString(1, newUser.getFirstName());
            statement.setString(2, newUser.getLastName());
            statement.setString(3, newUser.getPassword());
            statement.setString(4, newUser.getBirthday().toString());
            statement.setString(5, newUser.getGender().toString());
            statement.setLong(6, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    @Override
    public void removeById(Long id) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE snet.users SET deleted=TRUE WHERE userId=?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Can't remove user", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Date birthday = resultSet.getDate("birthday");
        String gender = resultSet.getString("gender");
        Long imageId = resultSet.getLong("imageId");
        Image photo = Image.builder().id(imageId).build();

        return User.builder()
                .id(resultSet.getLong("userId"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("passHash"))
                .firstName(resultSet.getString("firstName"))
                .lastName(resultSet.getString("lastName"))
                .birthday(birthday.toLocalDate())
                .gender(gender.equals("MALE") ? Gender.MALE : Gender.FEMALE)
                .photo(photo)
                .deleted(resultSet.getBoolean("deleted")).build();
    }
}
