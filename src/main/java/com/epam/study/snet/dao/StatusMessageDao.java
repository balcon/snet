package com.epam.study.snet.dao;

import com.epam.study.snet.entity.StatusMessage;
import com.epam.study.snet.entity.User;

public interface StatusMessageDao {
    StatusMessage create(User user, String body) throws DaoException;

    StatusMessage getByUserId(long userId) throws DaoException;
}
