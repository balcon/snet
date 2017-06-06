package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.model.FormValidation;
import com.epam.study.snet.validators.LoginValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(LoginServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginValidator validator = LoginValidator.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password")).build();

        try {
            FormValidation formValidation = validator.validate();
            if (formValidation.isValid()) {
                User user = DaoFactory.getFactory().getUserDao().getByUsername(validator.getUsername());
                req.getSession().setAttribute("loggedUser", user);
                log.info("[" + user.getUsername() + "](id:[" + user.getId() + "]) logged in");
                String contextPath = req.getContextPath();
                resp.sendRedirect(contextPath + "/main");
            } else {
                req.setAttribute("formValidation", formValidation);
                req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            }
        } catch (DaoException e) {
            log.error("Error when logging in process",e);
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}