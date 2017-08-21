package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.StatusMessageDao;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.model.Countries;
import com.epam.study.snet.model.FormValidation;
import com.epam.study.snet.model.HashPass;
import com.epam.study.snet.validators.ProfileValidator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/registration")

public class RegistrationServlet extends HttpServlet {
    static private Logger log = Logger.getLogger(RegistrationServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale =(Locale) req.getSession().getAttribute("locale");
        req.setAttribute("countries", new Countries(locale));
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProfileValidator profile = ProfileValidator.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .confirmPassword(req.getParameter("confirmPassword"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthday(req.getParameter("birthday"))
                .country(req.getParameter("country"))
                .gender(req.getParameter("gender")).build();

        try {
            FormValidation formValidation = profile.validate();
            if (formValidation.isValid()) formValidation = profile.checkUsername();
            if (formValidation.isValid()) {
                profile.hashPass(new HashPass());
                StatusMessageDao statusMessageDao=DaoFactory.getFactory().getStatusMessageDao();
                User user = DaoFactory.getFactory().getUserDao(statusMessageDao).create(profile);
                log.info("Registered new user [" + user.getUsername() + "](ID:[" + user.getId() + "]");
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("formValidation", formValidation);
                Locale locale =(Locale) req.getSession().getAttribute("locale");
                req.setAttribute("countries", new Countries(locale));
                req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
            }
        } catch (DaoException e) {
            log.error("Error when registering new user", e);
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }


}
