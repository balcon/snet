package com.epam.study.snet.model.dao;

import com.epam.study.snet.model.entity.StatusMessage;
import com.epam.study.snet.model.entity.User;

public interface StatusMessageDao {
    StatusMessage create(User user, String body) throws DaoException;

    StatusMessage getByUserId(long userId) throws DaoException;
}
