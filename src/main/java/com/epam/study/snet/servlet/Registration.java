package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.RegistrationFields;
import com.epam.study.snet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/registration")

public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationFields fields = RegistrationFields.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .confirmPassword(req.getParameter("confirmPassword"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .gender(req.getParameter("gender")).build();


        Map<String, FormErrors> validation = fields.validate();
        UserDao userDao = DaoConfig.daoFactory.getUserDao();
        try {
            if (validation.isEmpty()) {
                if (userDao.getByUsername(req.getParameter("username")) != null) {
                    validation.put("username", FormErrors.username_exists);
                    req.setAttribute("validation", validation);
                    req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
                } else {
                    User user = fields.toUser();
                    DaoConfig.daoFactory.getUserDao().create(user);
                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            } else {
                req.setAttribute("validation", validation);
                req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);

            }
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
