package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.Message;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.model.ProfileFields;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class MySqlMessageDaoTest extends MySqlDaoTests {
    private static UserDao userDao = daoFactory.getUserDao();
    private static MessageDao messageDao = daoFactory.getMessageDao(userDao);

    private static User user1;
    private static User user2;
    private static User user3;

    private static Message testMessage;

    @BeforeClass
    public static void setUp() throws Exception {
        ProfileFields prof1 = ProfileFields.builder().username("u1").password("123").birthday(LocalDate.now().toString()).gender("FEMALE").country("US").build();
        user1 = userDao.create(prof1);
        ProfileFields prof2 = ProfileFields.builder().username("u2").password("123").birthday(LocalDate.now().toString()).gender("FEMALE").country("US").build();
        user2 = userDao.create(prof2);
        ProfileFields prof3 = ProfileFields.builder().username("u3").password("123").birthday(LocalDate.now().toString()).gender("FEMALE").country("US").build();
        user3 = userDao.create(prof3);

        testMessage = Message.builder().sender(user3).receiver(user2).body("Hi, u2!").build();

        messageDao.create(Message.builder().sender(user1).receiver(user2).body("from u1 to u2").build());
        messageDao.create(Message.builder().sender(user1).receiver(user3).body("from u1 to u3").build());
        messageDao.create(Message.builder().sender(user2).receiver(user3).body("from u2 to u3").build());
        messageDao.create(Message.builder().sender(user1).receiver(user2).body("from u1 to u2").build());
        messageDao.create(Message.builder().sender(user2).receiver(user1).body("from u2 to u1").build());
    }

    @Test
    public void createMessage() throws Exception {
        Message message = messageDao.create(testMessage);

        assertEquals(message.getSendingTime().toLocalDate(), LocalDate.now());
        assertEquals(message.getSender().getUsername(), "u3");
        assertEquals(message.getBody(), "Hi, u2!");
        assertTrue(message.getId() != 0);
    }

    @Test
    public void numberReadMessages() throws Exception {
        int number = messageDao.getNumberUnread(user3);

        assertEquals(number, 2);
    }

    @Test
    public void makeMessagesRead() throws Exception {
        int numberBefore = messageDao.getNumberUnread(user2);
        messageDao.makeReadBetweenUsers(user1, user2);
        int numberAfter = messageDao.getNumberUnread(user2);

        assertTrue(numberAfter < numberBefore);
    }

    @Test
    public void setIdOnceAgain() throws Exception {
        Message message = messageDao.create(testMessage);
        long badId = 100500;
        message.setId(badId);

        assertFalse(message.getId() == badId);
    }

    @Test
    @Ignore
    public void getListWithLastMessages() throws Exception {
        List<Message> messages = messageDao.getListOfLatest(user1);

        assertEquals(messages.size(), 2);
    }

    @Test
    public void getListBetweenUsers() throws Exception {
        List<Message> messages = messageDao.getListBetweenUsers(user1, user2);

        assertEquals(messages.size(), 3);
    }

    @Test
    public void getLimitedListBetweenUsers() throws Exception {
        List<Message> messages = messageDao.getListBetweenUsers(user1, user2, 0, 1);

        assertEquals(messages.size(), 1);
    }

    @Test
    public void getNumberMessages() throws Exception {
        int number = messageDao.getNumberBetweenUsers(user1, user2);

        assertEquals(number, 3);
    }

    @Test
    public void removeById() throws Exception {
        Message message = messageDao.create(testMessage);
        List<Message> messagesBefore = messageDao.getListBetweenUsers(user3, user2);
        messageDao.removeById(message.getId());
        List<Message> messagesAfter = messageDao.getListBetweenUsers(user3, user2);

        assertEquals(messagesBefore.size() - messagesAfter.size(), 1);
    }

    @Test
    public void getNumberUnreadBetweenUsers() throws Exception {
        int number = messageDao.getNumberUnreadBetweenUsers(user2, user3);

        assertEquals(number, 1);
    }
}