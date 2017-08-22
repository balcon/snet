package com.epam.study.snet.controller.servlet;

import com.epam.study.snet.model.dao.DaoException;
import com.epam.study.snet.model.entity.User;
import com.epam.study.snet.view.Messages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main/messages")
public class MessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");

        try {
            Messages messages = new Messages(loggedUser);
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("/WEB-INF/pages/messages.jsp").forward(req, resp);
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
