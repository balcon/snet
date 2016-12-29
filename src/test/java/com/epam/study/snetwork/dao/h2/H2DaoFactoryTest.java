package com.epam.study.snetwork.dao.h2;

import com.epam.study.snetwork.dao.DaoFactory;
import com.epam.study.snetwork.dao.UserDao;
import org.junit.Test;

import static org.junit.Assert.*;

public class H2DaoFactoryTest {

    @Test
    public void getUserDao() throws Exception {
        DaoFactory daoFactory=new H2DaoFactory();
        UserDao userDao=daoFactory.getUserDao();

        assertTrue(userDao instanceof H2UserDao);
    }
}