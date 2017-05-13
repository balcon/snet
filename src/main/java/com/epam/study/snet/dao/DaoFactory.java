package com.epam.study.snet.dao;

import com.epam.study.snet.dao.db.mysql.MySqlDaoFactory;

public interface DaoFactory {
    static DaoFactory getFactory() throws DaoException {
        return new MySqlDaoFactory();
    }
    UserDao getUserDao();
    MessageDao getMessageDao(UserDao userDao);
    ImageDao<byte[]> getImageDao();
}
