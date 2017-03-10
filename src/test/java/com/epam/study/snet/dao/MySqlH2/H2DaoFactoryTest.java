package com.epam.study.snet.dao.MySqlH2;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import org.junit.Test;

import static org.junit.Assert.*;

public class H2DaoFactoryTest {

    @Test
    public void getUserDao() throws Exception {
        DaoFactory daoFactory=new H2DaoFactory();
        UserDao userDao=daoFactory.getUserDao();

        assertTrue(userDao instanceof MySqlH2UserDao);
    }

    @Test
    public void getMessageDao() throws Exception {
        DaoFactory daoFactory=new H2DaoFactory();
        MessageDao messageDao=daoFactory.getMessageDao();

        assertTrue(messageDao instanceof MySqlH2MessageDao);
    }
}