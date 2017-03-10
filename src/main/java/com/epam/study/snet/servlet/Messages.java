package com.epam.study.snet.servlet;

import com.epam.study.snet.Static;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.model.Message;
import com.epam.study.snet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/main/messages")
public class Messages extends HttpServlet {
    UserDao userDao = Static.daoFactory.getUserDao();
    MessageDao messageDao = Static.daoFactory.getMessageDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sender = (User)req.getSession().getAttribute("user");
        List<Message> messages = messageDao.getListOfLastMessages(sender);
        List<User> users = userDao.getList();

        req.setAttribute("users", users);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/WEB-INF/pages/messages.jsp").forward(req, resp);
    }
}
