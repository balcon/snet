package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.model.LastMessage;
import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/main/messages")
public class Messages extends HttpServlet {
    private UserDao userDao = DaoConfig.daoFactory.getUserDao();
    private MessageDao messageDao = DaoConfig.daoFactory.getMessageDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        try {
            List<Message> messages = messageDao.getListOfLatest(loggedUser);
            List<LastMessage> lastMessages = new ArrayList<>();
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

            req.setAttribute("messages", messages);//TODO remove
            req.setAttribute("lastMessages", lastMessages);
            req.getRequestDispatcher("/WEB-INF/pages/messages.jsp").forward(req, resp);
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
