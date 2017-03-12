package com.epam.study.snet.dao;

import com.epam.study.snet.model.User;

import java.util.List;

public interface UserDao {
    User create( String username, String passHash, String firstName, String lastName);

    List<User> getList();

    User getById(Long id);

    User getByUsername(String username);
}
