package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.Country;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.model.Image;
import com.epam.study.snet.validators.ProfileValidator;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao implements UserDao {
    private static Logger log = Logger.getLogger(MySqlUserDao.class.getCanonicalName());
    private final DataSource dataSource;

    MySqlUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(ProfileValidator profile) throws DaoException {
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.users (username, passHash, firstName, lastName, birthday, gender, country)" +
                            " VALUES (?,?,?,?,?,?,?)");
            statement.setString(1, profile.getUsername());
            statement.setString(2, profile.getPassword());
            statement.setString(3, profile.getFirstName());
            statement.setString(4, profile.getLastName());
            statement.setString(5, profile.getBirthday());
            statement.setString(6, profile.getGender());
            statement.setString(7, profile.getCountry());
            statement.execute();

            log.debug("MySQL INSERT [snet.users]");

            long userId = 0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            }
            LocalDate birthday = LocalDate.parse(profile.getBirthday());
            Gender gender = profile.getGender().equals("MALE") ? Gender.MALE : Gender.FEMALE;
            Country country = new Country(profile.getCountry());

            user = User.builder()
                    .id(userId)
                    .username(profile.getUsername())
                    .password(profile.getPassword())
                    .firstName(profile.getFirstName())
                    .lastName(profile.getLastName())
                    .birthday(birthday)
                    .gender(gender)
                    .country(country).build();

        } catch (SQLException e) {
            throw new DaoException("Can't create user", e);
        }
        return user;
    }

    @Override
    public List<User> getList() throws DaoException {
        String queryString = "SELECT * FROM snet.users WHERE deleted=FALSE";
        return executeGetList(queryString);
    }

    @Override
    public List<User> getList(User excludedUser) throws DaoException {
        String queryString = "SELECT * FROM snet.users WHERE userId!=? AND deleted=FALSE ORDER BY lastName,firstName";
        return executeGetList(queryString, excludedUser.getId());
    }

    @Override
    public List<User> getList(User excludedUser, Country country) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.users WHERE userId!=? AND deleted=FALSE" +
                            " AND country=? ORDER BY lastName,firstName");
            statement.setLong(1, excludedUser.getId());
            statement.setString(2, country.getCode());
            ResultSet resultSet = statement.executeQuery();
            log.debug("MySQL SELECT [snet.users]");
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
        String queryString = "SELECT * FROM snet.users WHERE userId!=? AND deleted=FALSE" +
                " ORDER BY lastName,firstName LIMIT ?,?";
        return executeGetList(queryString, excludedUser.getId(), skip, limit);
    }

    @Override
    public User getById(long id) throws DaoException {
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM snet.users WHERE userId=?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            log.debug("MySQL SELECT [snet.users]");
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
                    "SELECT * FROM snet.users WHERE username=? AND deleted=FALSE");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            log.debug("MySQL SELECT [snet.users]");
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
            log.debug("MySQL SELECT [snet.users]");
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
    public void updateById(long id, ProfileValidator profile) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE snet.users SET firstName=?,lastName=?,passHash=?,birthday=?,gender=?,country=?,username=?" +
                            " WHERE userId=?");
            statement.setString(1, profile.getFirstName());
            statement.setString(2, profile.getLastName());
            statement.setString(3, profile.getPassword());
            statement.setString(4, profile.getBirthday());
            statement.setString(5, profile.getGender());
            statement.setString(6, profile.getCountry());
            statement.setString(7, profile.getUsername());
            statement.setLong(8, id);
            statement.execute();
            log.debug("MySQL UPDATE [snet.users]");
        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    @Override
    public void removeById(long id) throws DaoException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE snet.users SET deleted=TRUE WHERE userId=?");
            statement.setLong(1, id);
            statement.execute();
            log.debug("MySQL UPDATE [snet.users]");
        } catch (SQLException e) {
            throw new DaoException("Can't remove user", e);
        }
    }

    @Override
    public List<Country> getCountries() throws DaoException {
        List<Country> countries = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT country FROM snet.users WHERE deleted=FALSE GROUP BY country");
            ResultSet resultSet = statement.executeQuery();
            log.debug("MySQL SELECT [snet.users]");
            while (resultSet.next()) {
                countries.add(new Country(resultSet.getString("country")));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get userlist", e);
        }
        return countries;
    }

    private List<User> executeGetList(String queryString, long... param) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(queryString);
            for (int i = 0; i < param.length; i++) {
                statement.setLong(i + 1, param[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            log.debug("MySQL SELECT [snet.users]");
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't get userlist", e);
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Date birthday = resultSet.getDate("birthday");
        String gender = resultSet.getString("gender");
        String country = resultSet.getString("country");
        Long imageId = resultSet.getLong("imageId");
        Image photo = Image.builder().id(imageId).build();

        return User.builder()
                .id(resultSet.getLong("userId"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("passHash"))
                .firstName(resultSet.getString("firstName"))
                .lastName(resultSet.getString("lastName"))
                .birthday(birthday.toLocalDate())
                .country(new Country(country))
                .gender(gender.equals("MALE") ? Gender.MALE : Gender.FEMALE)
                .photo(photo)
                .deleted(resultSet.getBoolean("deleted")).build();
    }
}
