package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.model.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MySqlUserDaoTest extends MySqlDaoTests {
    @Test
    public void createUser() throws Exception {
        User user = userDao.create("pit", "123","Piter", "Johnson");
        System.out.println(user);
        assertEquals(user.getFirstName(), "Piter");
        assertTrue(user.getId() != 0);
    }

    @Test
    public void getUserList() throws Exception {
        List<User> users = userDao.getList();

        assertFalse(users.isEmpty());
    }

    @Test
    public void getUserById() throws Exception {
        User user1 = userDao.create("mt", "pass","Mister", "Twister");
        Long userId = user1.getId();
        User user2 = userDao.getById(userId);

        assertEquals(user1, user2);
    }

    @Test
    public void getUserByUsername() throws Exception {
        User user1 = userDao.create("user1username", "pass","userFn", "userLn");
        User user2 = userDao.getByUsername("user1username");

        assertEquals(user1, user2);
    }
}