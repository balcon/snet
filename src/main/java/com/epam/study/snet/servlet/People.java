package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/main/people")
public class People extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = DaoConfig.daoFactory.getUserDao();
        try {
            List<User> users = userDao.getList();
            req.setAttribute("users",users);
            req.getRequestDispatcher("/WEB-INF/pages/people.jsp").forward(req, resp);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
