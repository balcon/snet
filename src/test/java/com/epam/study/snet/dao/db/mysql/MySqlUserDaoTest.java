package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.Country;
import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.entity.User;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class MySqlUserDaoTest extends MySqlDaoTests {
    private UserDao userDao=daoFactory.getUserDao();

    private User testUser = User.builder()
            .username("pit")
            .password("123")
            .firstName("Peter")
            .lastName("Johnson")
            .country(new Country("UK"))
            .birthday(LocalDate.of(1980, 5, 12))
            .gender(Gender.MALE).build();

    @Test
    public void createUser() throws Exception {
        User user = userDao.create(testUser);

        assertTrue(user.getId() != 0);
        assertEquals(user.getFirstName(), "Peter");
        assertEquals(user.getLastName(), "Johnson");
        assertEquals(user.getBirthday(), LocalDate.of(1980, 5, 12));
        assertEquals(user.getGender(), Gender.MALE);
        assertEquals(user.getCountry(), new Country("UK"));
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

        assertFalse(user1.isDeleted());
        assertEquals(user2.getFirstName(), "Peter");
        assertEquals(user2.getLastName(), "Johnson");
        assertEquals(user2.getBirthday(), LocalDate.of(1980, 5, 12));
        assertEquals(user2.getGender(), Gender.MALE);
        assertEquals(user2.getCountry(), new Country("UK"));
    }

    @Test
    public void getNumber() throws Exception {
        User user = userDao.create(testUser);
        long number = userDao.getNumber();

        assertTrue(number != 0);

        userDao.removeById(user.getId());
        long numberAfterRemove = userDao.getNumber();

        assertEquals(number - numberAfterRemove, 1);
    }

    @Test
    public void getListWithExclude() throws Exception {
        User user = User.builder()
                .username("u")
                .password("p")
                .firstName("f")
                .lastName("l")
                .birthday(LocalDate.now())
                .country(new Country("US"))
                .gender(Gender.MALE).build();
        user = userDao.create(user);
        User user2 = userDao.create(testUser);
        List<User> users = userDao.getList();
        List<User> usersExcluded = userDao.getList(user);

        int allUsers = users.size();
        int usersWithoutUser = usersExcluded.size();
        assertEquals(allUsers - usersWithoutUser, 1);

        userDao.removeById(user2.getId());

        usersExcluded = userDao.getList(user);
        int usersWithoutUserAfterRemove = usersExcluded.size();

        assertEquals(usersWithoutUser - usersWithoutUserAfterRemove, 1);
    }

    @Test
    public void getLimitedList() throws Exception {
        User user = User.builder()
                .username("u2")
                .password("p")
                .firstName("f")
                .lastName("l")
                .country(new Country("US"))
                .birthday(LocalDate.now())
                .gender(Gender.MALE).build();
        user = userDao.create(user);
        User user2 = userDao.create(testUser);

        List<User> users = userDao.getList(user, 0, 1);

        assertEquals(users.size(), 1);

        int size = userDao.getList(user, 0, 100).size();
        userDao.removeById(user2.getId());
        int sizeAfterRemove = userDao.getList(user, 0, 100).size();

        assertEquals(size - sizeAfterRemove, 1);
    }

    @Test
    public void getUserByUsername() throws Exception {
        User user = User.builder()
                .username("pit2")
                .password("123")
                .firstName("Peter")
                .lastName("Johnson")
                .birthday(LocalDate.now())
                .country(new Country("US"))
                .gender(Gender.MALE).build();
        User user1 = userDao.create(user);
        User user2 = userDao.getByUsername("pit2");

        assertEquals(user1.getId(), user2.getId());
    }

    @Test
    public void removeUserById() throws Exception {
        User user = User.builder()
                .username("u5")
                .password("pass")
                .firstName("Timmy")
                .lastName("Robertson")
                .country(new Country("US"))
                .birthday(LocalDate.now())
                .gender(Gender.MALE).build();
        user = userDao.create(user);
        Long userId = user.getId();

        assertFalse(user.isDeleted());

        userDao.removeById(userId);
        user = userDao.getById(userId);

        assertTrue(user.isDeleted());
    }

    @Test
    public void updateUserById() throws Exception {
        User user1 = User.builder()
                .username("u5")
                .password("pass")
                .firstName("Timmy")
                .lastName("Robertson")
                .birthday(LocalDate.now())
                .country(new Country("US"))
                .gender(Gender.MALE).build();
        user1 = userDao.create(user1);

        User user2 = User.builder()
                .username(user1.getUsername())
                .password("newPass")
                .firstName(user1.getFirstName())
                .lastName("newLastName")
                .birthday(user1.getBirthday())
                .country(new Country("UK"))
                .gender(user1.getGender()).build();

        userDao.updateById(user1.getId(), user2);

        User user1new = userDao.getById(user1.getId());

        assertTrue(user1.getFirstName().equals(user1new.getFirstName()));
        assertTrue(user1.getBirthday().equals(user1new.getBirthday()));
        assertFalse(user1.getLastName().equals(user1new.getLastName()));
        assertFalse(user1.getPassword().equals(user1new.getPassword()));
        assertFalse(user1.getCountry().equals(user1new.getCountry()));
    }
}