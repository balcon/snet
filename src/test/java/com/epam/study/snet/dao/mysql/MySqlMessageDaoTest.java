package com.epam.study.snet.dao.mysql;

import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MySqlMessageDaoTest extends MySqlDaoTests {

    User user1=userDao.create("u1","u1","u1","u1");
    User user2=userDao.create("u2","u2","u2","u2");
    User user3=userDao.create("u3","u3","u3","u3");

    @Test
    public void createMessage() throws Exception {

        Message message = messageDao.createMessage(user1, user2, "Hi, u2!");

        assertEquals(message.getSendingTime().toLocalDate(), LocalDate.now());
        assertEquals(message.getSender().getFirstName(), "u1");
        assertEquals(message.getBody(), "Hi, u2!");
        assertTrue(message.getId() != 0);
    }

    @Test
    public void getListWithLastMessages() throws Exception {
        messageDao.createMessage(user1,user2,"m1");
        messageDao.createMessage(user2,user1,"m2");
        messageDao.createMessage(user2,user3,"m3");
        messageDao.createMessage(user1,user3,"m4");
        messageDao.createMessage(user3,user1,"m5");

        List<Message> messages=messageDao.getListOfLastMessages(user1);

        assertEquals(messages.size(),2);
    }

    @Test
    public void getListBySenderAndReceiver() throws Exception {
        messageDao.createMessage(user1,user2,"m1");
        messageDao.createMessage(user2,user1,"m2");
        messageDao.createMessage(user2,user3,"m3");
        messageDao.createMessage(user1,user3,"m4");
        messageDao.createMessage(user3,user1,"m5");

        List<Message> messages=messageDao.getListBySenderAndReceiver(user1,user2);

        assertEquals(messages.size(),2);
    }
}