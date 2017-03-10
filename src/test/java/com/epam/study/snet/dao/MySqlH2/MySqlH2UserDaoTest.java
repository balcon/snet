package com.epam.study.snet.dao.MySqlH2;

import com.epam.study.snet.model.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MySqlH2UserDaoTest extends H2DaoTests {
    @Test
    public void createUser() throws Exception {
        User user= userDao.create("Piter", "Johnson", "pit", "123");

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
        User user1 = userDao.create("Mister", "Twister", "mt", "pass");
        Long userId = user1.getId();
        User user2 = userDao.getById(userId);

        assertEquals(user1, user2);
    }

    @Test
    public void getUserByUsername() throws Exception {
        User user1=userDao.create("userFn","userLn","user1username","pass");
        User user2=userDao.getByUsername("user1username");

        assertEquals(user1,user2);
    }
}