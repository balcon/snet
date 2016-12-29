package com.epam.study.snetwork.dao;

import com.epam.study.snetwork.model.User;

import java.util.List;

public interface UserDao {
    User create(String firstName, String lastName, String username, String passHash);

    List<User> getList();
}
