package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class MySqlMessageDaoTest extends MySqlDaoTests {

    private User user1;
    private User user2;
    private User user3;

    private Message testMessage;


    @Before
    public void setUp() throws Exception {
        user1 = userDao.create(User.builder().username("u1").birthday(LocalDate.now()).gender(Gender.FEMALE).build());
        user2 = userDao.create(User.builder().username("u2").birthday(LocalDate.now()).gender(Gender.FEMALE).build());
        user3 = userDao.create(User.builder().username("u3").birthday(LocalDate.now()).gender(Gender.FEMALE).build());

        testMessage = Message.builder().sender(user1).receiver(user2).body("Hi, u2!").build();

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
        assertEquals(message.getSender().getUsername(), "u1");
        assertEquals(message.getBody(), "Hi, u2!");
        assertTrue(message.getId() != 0);
    }

    @Test
    public void numberReadMessages() throws Exception {
        int number = messageDao.getNumberUnreadMessages(user3);

        assertEquals(number, 2);
    }

    @Test
    public void makeMessagesRead() throws Exception {
        int numberBefore=messageDao.getNumberUnreadMessages(user2);
        messageDao.makeMessagesRead(user1,user2);
        int numberAfter=messageDao.getNumberUnreadMessages(user2);

        assertTrue(numberAfter<numberBefore);
    }

    @Test
    public void setIdOnceAgain() throws Exception {
        Message message = messageDao.create(testMessage);
        long badId = 100500;
        message.setId(badId);

        assertFalse(message.getId() == badId);
    }

    @Ignore
    @Test
    public void getListWithLastMessages() throws Exception {
        //TODO: heisenbag
        List<Message> messages = messageDao.getListOfLastMessages(user1);

        assertEquals(messages.size(), 2);
    }

    @Test
    public void getListBySenderAndReceiver() throws Exception {
        List<Message> messages = messageDao.getListBySenderAndReceiver(user1, user2);
        assertTrue(messages.get(0).isUnread());
        assertEquals(messages.size(), 3);
    }
}