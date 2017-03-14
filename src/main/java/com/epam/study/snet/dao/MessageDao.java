package com.epam.study.snet.dao;

import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;

import java.util.List;

public interface MessageDao {
    Message create(Message message) throws DaoException;

    List<Message> getListOfLastMessages(User user) throws DaoException;

    List<Message> getListBySenderAndReceiver(User sender, User receiver) throws DaoException;
}
