package com.epam.study.snetwork.dao.h2;

import com.epam.study.snetwork.dao.UserDao;
import com.epam.study.snetwork.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2UserDao implements UserDao {
    private final DataSource dataSource;

    public H2UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User create(String firstName, String lastName, String username, String passHash) {
        User user=null;
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO snet.users (firstName, lastName, username, passHash) VALUES (?,?,?,?)");
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setString(3,username);
            statement.setString(4,passHash);
            statement.execute();
            long userId=0;
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next())
                userId=generatedKeys.getLong(1);
            user= User.builder()
                    .id(userId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .passHash(passHash)
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getList() {
        List<User> users = new ArrayList<>();
        return users;
    }
}
