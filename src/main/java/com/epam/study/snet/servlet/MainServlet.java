package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.model.Countries;
import com.epam.study.snet.model.People;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale = (Locale) req.getSession().getAttribute("locale");
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        try {
            List<User> friends = DaoFactory.getFactory().getUserDao().getList(loggedUser,loggedUser.getCountry());
            req.setAttribute("countries", new Countries(locale));
            req.setAttribute("friends", friends);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}
