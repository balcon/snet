package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.dao.DaoConfig;
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
    private final UserDao userDao = DaoConfig.daoFactory.getUserDao();
    private final MessageDao messageDao = DaoConfig.daoFactory.getMessageDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        User companion = null;
        try {
            companion = userDao.getById(Long.valueOf(req.getParameter("companionId")));
            List<Message> messages = messageDao.getListBySenderAndReceiver(currentUser, companion);
            req.setAttribute("messages", messages);
            req.setAttribute("companionId", req.getParameter("companionId"));
            req.getRequestDispatcher("/WEB-INF/pages/chat.jsp").forward(req, resp);
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/fatalErrorPage.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sender = (User) req.getSession().getAttribute("user");
        try {
            User receiver = userDao.getById(Long.valueOf(req.getParameter("companionId")));
            String body = req.getParameter("body");
            Message message = Message.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .body(body).build();
            messageDao.create(message);
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/main/chat?companionId=" + req.getParameter("companionId"));
        }catch (DaoException e){
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/fatalErrorPage.jsp").forward(req, resp);
        }
    }
}
