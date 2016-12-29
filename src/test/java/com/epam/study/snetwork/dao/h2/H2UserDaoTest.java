package com.epam.study.snetwork.dao.h2;

import com.epam.study.snetwork.model.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class H2UserDaoTest extends H2DaoTests {
    @Test
    public void createUser() throws Exception {
        User user = userDao.create("Piter", "Johnson", "pit", "123");

        assertEquals(user.getFirstName(), "Piter");
        assertTrue(user.getId() != 0);
    }

    @Test
    public void getList() throws Exception {
        List<User> users = userDao.getList();

        assertFalse(users.isEmpty());
    }

    @Test
    public void getById() throws Exception {
        User user1 = userDao.create("Mister", "Twister", "mt", "pass");
        Long userId = user1.getId();
        User user2 = userDao.getById(userId);

        assertEquals(user1, user2);
    }
}