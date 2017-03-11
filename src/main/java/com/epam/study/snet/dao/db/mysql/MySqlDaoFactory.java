package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;

import javax.sql.DataSource;

public class MySqlDaoFactory implements DaoFactory {
    private final DataSource dataSource;

    public MySqlDaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao(dataSource);
    }

    @Override
    public MessageDao getMessageDao() {
        return new MySqlMessageDao(dataSource);
    }
}
