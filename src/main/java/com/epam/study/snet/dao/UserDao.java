package com.epam.study.snet.dao;

import com.epam.study.snet.model.User;

import java.util.List;

public interface UserDao {
    User create(String firstName, String lastName, String username, String passHash);

    List<User> getList();

    User getById(Long id);

    User getByUsername(String username);
}
