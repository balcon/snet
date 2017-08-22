package com.epam.study.snet.view;

import com.epam.study.snet.model.dao.*;
import com.epam.study.snet.model.entity.Message;
import com.epam.study.snet.model.entity.User;
import lombok.Value;

import java.util.List;

//todo validate chat with itself
@Value
public class Chat {
    final int LIMIT = 10;
    List<Message> messages;
    User companion;
    int numberPages;
    int activePage;

    public Chat(User loggedUser, String companionId, String page) throws DaoException {
        StatusMessageDao statusMessageDao=DaoFactory.getFactory().getStatusMessageDao();
        UserDao userDao = DaoFactory.getFactory().getUserDao(statusMessageDao);
        MessageDao messageDao = DaoFactory.getFactory().getMessageDao(userDao);
        companion = userDao.getById(Long.valueOf(companionId));
        int numberMessages = messageDao.getNumberBetweenUsers(loggedUser, companion);
        numberPages = (numberMessages - 1) / LIMIT + 1;
        if (page != null) {
            activePage = Integer.valueOf(page);
            if (activePage > 0)
                messages = messageDao.getListBetweenUsers(loggedUser, companion, (activePage - 1) * LIMIT, LIMIT);
            else
                messages = messageDao.getListBetweenUsers(loggedUser, companion);
        } else {
            activePage = 1;
            messages = messageDao.getListBetweenUsers(loggedUser, companion, (activePage - 1) * LIMIT, LIMIT);
        }
        messageDao.makeReadBetweenUsers(companion, loggedUser);

    }
}
