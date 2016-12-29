package com.epam.study.snetwork.dao.h2;

import com.epam.study.snetwork.model.User;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;

public class H2UserDaoTest extends H2DaoTests{
    @Test
    public void createUser() throws Exception {
       User user=userDao.create("Piter","Johnson","pit","123");

       assertEquals(user.getFirstName(),"Piter");
       assertTrue(user.getId()!=0);
    }

    @Test
    public void getList() throws Exception {
        List<User> users=userDao.getList();

        assertFalse(users.isEmpty());
    }
}