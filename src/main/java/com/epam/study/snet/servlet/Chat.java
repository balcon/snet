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

@WebServlet("/main/chat")
public class Chat extends HttpServlet {
    UserDao userDao = Static.daoFactory.getUserDao();
    MessageDao messageDao = Static.daoFactory.getMessageDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        User companion = userDao.getById(Long.valueOf(req.getParameter("companionId")));
        List<Message> messages = messageDao.getListBySenderAndReceiver(currentUser, companion);
        req.setAttribute("messages", messages);
        req.setAttribute("companionId", req.getParameter("companionId"));
        req.getRequestDispatcher("/WEB-INF/pages/chat.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sender = (User) req.getSession().getAttribute("user");
        User receiver = userDao.getById(Long.valueOf(req.getParameter("companionId")));
        String body = req.getParameter("body");

        messageDao.createMessage(sender, receiver, body);

        String contextPath = req.getContextPath();
        resp.sendRedirect(contextPath + "/main/chat?companionId=" + req.getParameter("companionId"));
    }
}
