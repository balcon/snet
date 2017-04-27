package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.MessageDao;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.Message;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.model.Chat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main/chat")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        try {
            Chat chat = new Chat(loggedUser, req.getParameter("companionId"), req.getParameter("page"));
            req.setAttribute("chat", chat);
            req.getRequestDispatcher("/WEB-INF/pages/chat.jsp").forward(req, resp);
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User sender = (User) req.getSession().getAttribute("loggedUser");
        try {
            UserDao userDao = DaoFactory.getFactory().getUserDao();
            MessageDao messageDao = DaoFactory.getFactory().getMessageDao();
            User receiver = userDao.getById(Long.valueOf(req.getParameter("companionId")));
            String action = req.getParameter("action");
            if (action != null && action.equals("remove")) {
                long messageId = Long.valueOf(req.getParameter("messageId"));
                messageDao.removeById(messageId);
                String contextPath = req.getContextPath();
                resp.sendRedirect(contextPath + "/main/chat?companionId=" + receiver.getId());
            } else {
                String body = req.getParameter("body");
                Message message = Message.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .body(body).build();
                messageDao.create(message);
                int unreadMessages = DaoFactory.getFactory().getMessageDao().getNumberUnread(sender);
                req.getSession().setAttribute("unreadMessages", unreadMessages);
                String contextPath = req.getContextPath();
                resp.sendRedirect(contextPath + "/main/chat?companionId=" + receiver.getId());
            }
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
