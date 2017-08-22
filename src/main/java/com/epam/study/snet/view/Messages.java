package com.epam.study.snet.view;

import com.epam.study.snet.model.dao.*;
import com.epam.study.snet.model.entity.Message;
import com.epam.study.snet.model.entity.User;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class Messages {
    List<LastMessage> lastMessages;

    public Messages(User loggedUser) throws DaoException {
        StatusMessageDao statusMessageDao=DaoFactory.getFactory().getStatusMessageDao();
        UserDao userDao=DaoFactory.getFactory().getUserDao(statusMessageDao);
        MessageDao messageDao = DaoFactory.getFactory().getMessageDao(userDao);
        List<Message> messages = messageDao.getListOfLatest(loggedUser);
        lastMessages = new ArrayList<>();
        for (Message message : messages) {
            User companion;
            Boolean response;
            if (message.getSender().equals(loggedUser)) {
                companion = message.getReceiver();
                response = true;
            } else {
                companion = message.getSender();
                response = false;
            }
            int numberUnread=messageDao.getNumberUnreadBetweenUsers(companion,loggedUser);
            lastMessages.add(LastMessage.builder()
                    .loggedUser(loggedUser)
                    .companion(companion)
                    .body(message.getBody())
                    .response(response)
                    .lastMessageTime(message.getSendingTime())
                    .numberUnread(numberUnread).build());
        }
    }
}
