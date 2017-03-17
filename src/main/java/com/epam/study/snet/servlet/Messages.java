package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.dao.DaoException;
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
    private UserDao userDao = DaoConfig.daoFactory.getUserDao();
    private MessageDao messageDao = DaoConfig.daoFactory.getMessageDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sender = (User) req.getSession().getAttribute("user");
        try {
            List<Message> messages = messageDao.getListOfLastMessages(sender);
            List<User> users = userDao.getList();

            req.setAttribute("users", users);
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("/WEB-INF/pages/messages.jsp").forward(req, resp);
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
