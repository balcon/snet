package com.epam.study.snet.model.dao;

import com.epam.study.snet.model.dao.db.mysql.MySqlDaoFactory;

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
