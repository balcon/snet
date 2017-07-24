package com.epam.study.snet.dao;

import com.epam.study.snet.dao.db.mysql.MySqlDaoFactory;
import com.epam.study.snet.entity.StatusMessage;

public interface DaoFactory {
    static DaoFactory getFactory() throws DaoException {
        return new MySqlDaoFactory();
    }

    UserDao getUserDao(StatusMessageDao statusMessageDao);
    MessageDao getMessageDao(UserDao userDao);
    ImageDao getImageDao();
    RelationshipDao getRelationshipDao();
    StatusMessageDao getStatusMessageDao();
}
