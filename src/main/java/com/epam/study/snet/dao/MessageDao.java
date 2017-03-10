package com.epam.study.snet.dao;

import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;

import java.util.List;

public interface MessageDao {
    Message createMessage(User sender, User reciever, String body);

    List<Message> getListOfLastMessages(User user);

    List<Message> getListBySenderAndReceiver(User sender, User receiver);
}
