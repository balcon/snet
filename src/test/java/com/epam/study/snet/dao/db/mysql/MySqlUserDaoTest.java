package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.Country;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.validators.ProfileValidator;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class MySqlUserDaoTest extends MySqlDaoTests {
    private UserDao userDao = daoFactory.getUserDao();

    private ProfileValidator testProfile = ProfileValidator.builder()
            .username("pit")
            .password("123")
            .firstName("Peter")
            .lastName("Johnson")
            .country("UK")
            .birthday(LocalDate.of(1980, 5, 12).toString())
            .gender("MALE").build();

    @Test
    public void createUser() throws Exception {
        User user = userDao.create(testProfile);

        assertTrue(user.getId() != 0);
        assertEquals("pit", user.getUsername());
        assertEquals("123", user.getPassword());
        assertEquals("Peter", user.getFirstName());
        assertEquals("Johnson", user.getLastName());
        assertEquals(LocalDate.of(1980, 5, 12), user.getBirthday());
        assertEquals(Gender.MALE, user.getGender());
        assertEquals(new Country("UK"), user.getCountry());
    }

    @Test
    public void getUserList() throws Exception {
        userDao.create(testProfile);
        List<User> users = userDao.getList();

        assertFalse(users.isEmpty());
    }

    @Test
    public void getUserById() throws Exception {
        User user1 = userDao.create(testProfile);
        Long userId = user1.getId();
        User user2 = userDao.getById(userId);
//todo photo ! test it!
        assertFalse(user1.isDeleted());
        assertEquals("Peter", user2.getFirstName());
        assertEquals("Johnson", user2.getLastName());
        assertEquals(LocalDate.of(1980, 5, 12), user2.getBirthday());
        assertEquals(Gender.MALE, user2.getGender());
        assertEquals(new Country("UK"), user2.getCountry());
    }

    @Test
    public void getNumber() throws Exception {
        User user = userDao.create(testProfile);
        long number = userDao.getNumber();

        assertTrue(number != 0);

        userDao.removeById(user.getId());
        long numberAfterRemove = userDao.getNumber();

        assertEquals(1, number - numberAfterRemove);
    }

    @Test
    public void getListWithExclude() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("u")
                .password("p")
                .firstName("f")
                .lastName("l")
                .birthday(LocalDate.now().toString())
                .country("US")
                .gender("MALE").build();
        User user = userDao.create(profile);
        User user2 = userDao.create(testProfile);
        List<User> users = userDao.getList();
        List<User> usersExcluded = userDao.getList(user);

        int allUsers = users.size();
        int usersWithoutUser = usersExcluded.size();
        assertEquals(1, allUsers - usersWithoutUser);

        userDao.removeById(user2.getId());

        usersExcluded = userDao.getList(user);
        int usersWithoutUserAfterRemove = usersExcluded.size();

        assertEquals(1, usersWithoutUser - usersWithoutUserAfterRemove);
    }

    @Test
    public void getLimitedList() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("u2")
                .password("p")
                .firstName("f")
                .lastName("l")
                .country("US")
                .birthday(LocalDate.now().toString())
                .gender("MALE").build();
        User user = userDao.create(profile);
        User user2 = userDao.create(testProfile);

        List<User> users = userDao.getList(user, 0, 1);

        assertEquals(1, users.size());

        int size = userDao.getList(user, 0, 100).size();
        userDao.removeById(user2.getId());
        int sizeAfterRemove = userDao.getList(user, 0, 100).size();

        assertEquals(1, size - sizeAfterRemove);
    }

    @Test
    public void getUserByUsername() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("pit2")
                .password("123")
                .firstName("Peter")
                .lastName("Johnson")
                .birthday(LocalDate.now().toString())
                .country("US")
                .gender("MALE").build();
        User user1 = userDao.create(profile);
        User user2 = userDao.getByUsername("pit2");

        assertTrue(user1.getId() == user2.getId());
    }

    @Test
    public void removeUserById() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("u5")
                .password("pass")
                .firstName("Timmy")
                .lastName("Robertson")
                .country("US")
                .birthday(LocalDate.now().toString())
                .gender("MALE").build();
        User user = userDao.create(profile);
        Long userId = user.getId();

        assertFalse(user.isDeleted());

        userDao.removeById(userId);
        user = userDao.getById(userId);

        assertTrue(user.isDeleted());
    }

    @Test
    public void updateUserById() throws Exception {
        ProfileValidator profile1 = ProfileValidator.builder()
                .username("u5")
                .password("pass")
                .firstName("Timmy")
                .lastName("Robertson")
                .birthday(LocalDate.now().toString())
                .country("US")
                .gender("MALE").build();

        ProfileValidator profile2 = ProfileValidator.builder()
                .username("newName")
                .password("newPass")
                .firstName("newFirst")
                .lastName("newLastName")
                .birthday(LocalDate.of(2012, 12, 12).toString())
                .country("UK")
                .gender("FEMALE").build();

        User user1 = userDao.create(profile1);
        userDao.updateById(user1.getId(), profile2);
        User user2 = userDao.getById(user1.getId());

        assertEquals("newName", user2.getUsername());
        assertEquals("newPass", user2.getPassword());
        assertEquals("newFirst", user2.getFirstName());
        assertEquals("newLastName", user2.getLastName());
        assertEquals(LocalDate.of(2012, 12, 12), user2.getBirthday());
        assertEquals(new Country("UK"), user2.getCountry());
        assertEquals(Gender.FEMALE, user2.getGender());
    }

    @Test
    public void getCountries() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("u6")
                .password("pass")
                .firstName("Timmy")
                .lastName("Robertson")
                .birthday(LocalDate.now().toString())
                .country("RU")
                .gender("MALE").build();
        userDao.create(profile);
        User user = userDao.create(testProfile);
        List<Country> countries = userDao.getCountries(user);

        assertTrue(countries.contains(new Country("RU")));
    }

    @Test
    public void getFilteredUserList() throws Exception {
        dropH2Schema();
        createH2Schema();
        ProfileValidator profile = ProfileValidator.builder()
                .username("u01")
                .password("pass")
                .firstName("test")
                .lastName("test")
                .country("US")
                .birthday(LocalDate.now().toString())
                .gender("MALE").build();
    }
}