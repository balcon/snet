package com.epam.study.snet.dao;

import com.epam.study.snet.entity.User;

import java.util.List;

public interface UserDao {
    User create(User user) throws DaoException;

    List<User> getList() throws DaoException;

    List<User> getList(User excludedUser) throws DaoException;

    List<User> getList(User excludedUser, long skip, int limit) throws DaoException;

    User getById(Long id) throws DaoException;

    User getByUsername(String username) throws DaoException;

    long getNumber() throws DaoException;

    void update(User user) throws DaoException;

    void updateById(Long id, User user2) throws DaoException;

    void removeById(Long id) throws DaoException;
}
