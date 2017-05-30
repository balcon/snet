package com.epam.study.snet.dao;

import com.epam.study.snet.entity.Message;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.validators.MessageValidator;

import java.util.List;

public interface MessageDao {
    Message create(MessageValidator messageValidator) throws DaoException;

    List<Message> getListOfLatest(User user) throws DaoException;

    List<Message> getListBetweenUsers(User user1, User user2) throws DaoException;

    List<Message> getListBetweenUsers(User user1, User user2, int skip, int limit) throws DaoException;

    int getNumberUnread(User receiver) throws DaoException;

    int getNumberUnreadBetweenUsers(User sender, User receiver) throws DaoException;

    int getNumberBetweenUsers(User user1, User user2) throws DaoException;

    void makeReadBetweenUsers(User sender, User receiver) throws DaoException;

    void removeById(long messageId) throws DaoException;

    Message getById(long messageId) throws DaoException;
}
