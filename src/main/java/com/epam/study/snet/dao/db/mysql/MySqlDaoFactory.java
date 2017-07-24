package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySqlDaoFactory implements DaoFactory {
    //todo logger?
    private static DataSource dataSource;

    //TODO wtf???
    public MySqlDaoFactory() throws DaoException {
        if (dataSource == null) {
            try {
                InitialContext initContext = new InitialContext();
                dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/appname");
            } catch (NamingException e) {
                throw new DaoException("Can't getByUser context", e);
            }
        }
    }

    MySqlDaoFactory(DataSource dSource) {
        dataSource = dSource;
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
    public StatusMessageDao getStatusMessageDao() {
        return new MySqlStatusMessageDao(dataSource);
    }

    @Override
    public UserDao getUserDao(StatusMessageDao statusMessageDao) {
        return new MySqlUserDao(dataSource, statusMessageDao);
    }

    @Override
    public MessageDao getMessageDao(UserDao userDao) {
        return new MySqlMessageDao(dataSource, userDao);
    }
}
