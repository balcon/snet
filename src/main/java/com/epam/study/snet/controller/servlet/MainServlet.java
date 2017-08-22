package com.epam.study.snet.controller.servlet;

import com.epam.study.snet.view.Main;
import com.epam.study.snet.model.dao.DaoException;
import com.epam.study.snet.model.dao.DaoFactory;
import com.epam.study.snet.model.dao.StatusMessageDao;
import com.epam.study.snet.model.dao.UserDao;
import com.epam.study.snet.model.entity.User;
import com.epam.study.snet.controller.services.Countries;
import com.epam.study.snet.controller.services.RelationManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale = (Locale) req.getSession().getAttribute("locale");
        User user;
        try {
            Main main = new Main();
            DaoFactory daoFactory = DaoFactory.getFactory();
            StatusMessageDao statusMessageDao = daoFactory.getStatusMessageDao();
            RelationManager relationManager = daoFactory.getRelationshipDao().getRelationManager();
            UserDao userDao = daoFactory.getUserDao(statusMessageDao);

            if (req.getParameter("id") == null) {
                user = (User) req.getSession().getAttribute("loggedUser");
                user = userDao.getById(user.getId());
                main.setItself(true);
            } else {
                long id = Long.valueOf(req.getParameter("id"));
                user = userDao.getById(id);
            }
            main.setUser(user);
            main.setCountries(new Countries(locale));
            main.setCompatriots(userDao.getList(user, user.getCountry()));
            main.setRelationManager(relationManager);
            req.setAttribute("main", main);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String messageBody = req.getParameter("messageBody");
        try {
            StatusMessageDao statusMessageDao = DaoFactory.getFactory().getStatusMessageDao();
            User user = (User) req.getSession().getAttribute("loggedUser");

            statusMessageDao.create(user, messageBody);
            resp.sendRedirect(req.getContextPath() + "/main");
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
