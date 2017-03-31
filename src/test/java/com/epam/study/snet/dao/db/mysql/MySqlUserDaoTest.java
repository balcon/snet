package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.model.User;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class MySqlUserDaoTest extends MySqlDaoTests {

    private User testUser = User.builder()
            .username("pit")
            .password("123")
            .firstName("Peter")
            .lastName("Johnson")
            .birthday(LocalDate.of(1980, 5, 12))
            .gender(Gender.MALE).build();

    @Test
    public void createUser() throws Exception {
        User user = userDao.create(testUser);

        assertEquals(user.getFirstName(), "Peter");
        assertTrue(user.getId() != 0);
    }

    @Test
    public void setIdOnceAgain() throws Exception {
        User user = userDao.create(testUser);
        long badId = 100500;
        user.setId(badId);

        assertFalse(user.getId() == badId);
    }

    @Test
    public void getUserList() throws Exception {
        List<User> users = userDao.getList();

        assertFalse(users.isEmpty());
    }

    @Test
    public void getUserById() throws Exception {
        User user1 = userDao.create(testUser);
        Long userId = user1.getId();
        User user2 = userDao.getById(userId);

        assertEquals(user1.getId(), user2.getId());
    }

    @Test
    public void getNumber() throws Exception {
        long number=userDao.getNumber();

        assertTrue(number!=0);
    }

    @Test
    public void getListWithExclude() throws Exception {
        User user = User.builder()
                .username("u")
                .password("p")
                .firstName("f")
                .lastName("l")
                .birthday(LocalDate.now())
                .gender(Gender.MALE).build();
        user = userDao.create(user);
        List<User> users = userDao.getList();
        List<User> usersExcluded = userDao.getList(user);

        assertEquals(users.size()-usersExcluded.size(),1);
    }

    @Test
    public void getLimitedList() throws Exception {
        User user = User.builder()
                .username("u2")
                .password("p")
                .firstName("f")
                .lastName("l")
                .birthday(LocalDate.now())
                .gender(Gender.MALE).build();
        user = userDao.create(user);

        List<User> users=userDao.getList(user,0,1);

        assertEquals(users.size(),1);
    }

    @Test
    public void getUserByUsername() throws Exception {
        User user = User.builder()
                .username("pit2")
                .password("123")
                .firstName("Peter")
                .lastName("Johnson")
                .birthday(LocalDate.now())
                .gender(Gender.MALE).build();
        User user1 = userDao.create(user);
        User user2 = userDao.getByUsername("pit2");

        assertEquals(user1.getId(), user2.getId());
    }
}