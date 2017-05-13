package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.ImageDao;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MySqlDaoFactoryTest {

    private final JdbcDataSource dataSource = new JdbcDataSource();

    @Test
    public void getUserDao() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory(dataSource);
        UserDao userDao = daoFactory.getUserDao();

        assertTrue(userDao instanceof MySqlUserDao);
    }

    @Test
    public void getMessageDao() throws Exception {
        DaoFactory daoFactory = new MySqlDaoFactory(dataSource);
        UserDao userDao=daoFactory.getUserDao();
        MessageDao messageDao = daoFactory.getMessageDao(userDao);

        assertTrue(messageDao instanceof MySqlMessageDao);
    }

    @Test
    public void getImageDao() throws Exception {
        DaoFactory daoFactory=new MySqlDaoFactory(dataSource);
        ImageDao imageDao=daoFactory.getImageDao();

        assertTrue(imageDao instanceof MySqlImageDao);
    }
}