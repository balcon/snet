package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySqlDaoFactory implements DaoFactory {
    private DataSource dataSource;

    public MySqlDaoFactory() throws DaoException {
        try {
            InitialContext initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
        } catch (NamingException e) {
            throw new DaoException("Can't get context", e);
        }
    }

    MySqlDaoFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ImageDao getImageDao() {
        return new MySqlImageDao(dataSource);
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
