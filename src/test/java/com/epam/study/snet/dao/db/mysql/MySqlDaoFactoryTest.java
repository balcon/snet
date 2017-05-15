package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.*;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MySqlDaoFactoryTest {

    private final JdbcDataSource dataSource = new JdbcDataSource();
    DaoFactory daoFactory = new MySqlDaoFactory(dataSource);

    @Test
    public void getUserDao() throws Exception {
        UserDao userDao = daoFactory.getUserDao();

        assertTrue(userDao instanceof MySqlUserDao);
    }

    @Test
    public void getMessageDao() throws Exception {
        UserDao userDao = daoFactory.getUserDao();
        MessageDao messageDao = daoFactory.getMessageDao(userDao);

        assertTrue(messageDao instanceof MySqlMessageDao);
    }

    @Test
    public void getImageDao() throws Exception {
        ImageDao imageDao = daoFactory.getImageDao();

        assertTrue(imageDao instanceof MySqlImageDao);
    }

    @Test
    public void getRelationsDao() throws Exception {
        RelationshipDao relationsDao = daoFactory.getRelationshipDao();

        assertTrue(relationsDao instanceof MySqlRelationshipDao);
    }
}