package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.*;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySqlDaoFactory implements DaoFactory {
    //todo logger?
    private DataSource dataSource;

    //TODO wtf???
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
    public RelationshipDao getRelationshipDao() {
        return new MySqlRelationshipDao(dataSource);
    }

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao(dataSource);
    }

    @Override
    public MessageDao getMessageDao(UserDao userDao) {
        return new MySqlMessageDao(dataSource, userDao);
    }
}
