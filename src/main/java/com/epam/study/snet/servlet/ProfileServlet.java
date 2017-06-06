package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.User;
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

@WebServlet("/main/profile")
public class ProfileServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ProfileServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        ProfileValidator profile;
        FormValidation formValidation;

        String action = req.getParameter("action");
        switch (action) {
            case "removeProfile":
                try {
                    DaoFactory.getFactory().getUserDao().removeById(loggedUser.getId());
                    req.getSession().setAttribute("loggedUser", null);
                    log.info("Profile of [" + loggedUser.getUsername() + "](id:[" + loggedUser.getId() + "]) removed");
                    String contextPath = req.getContextPath();
                    resp.sendRedirect(contextPath + "/main");
                } catch (DaoException e) {
                    log.error("Error when removing profile of [" + loggedUser.getUsername() + "](id:[" + loggedUser.getId() + "])", e);
                    req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
                }
                return;
            case "changePassword":
                profile = ProfileValidator.builder()
                        .username(loggedUser.getUsername())
                        .password(req.getParameter("password"))
                        .confirmPassword(req.getParameter("confirmPassword"))
                        .firstName(loggedUser.getFirstName())
                        .lastName(loggedUser.getLastName())
                        .birthday(loggedUser.getBirthday().toString())
                        .country(loggedUser.getCountry().getCode())
                        .gender(loggedUser.getGender().toString()).build();
                formValidation = profile.validate();
                profile.hashPass(new HashPass());
                break;
            default:
                profile = ProfileValidator.builder()
                        .username(loggedUser.getUsername())
                        .password(loggedUser.getPassword())
                        .confirmPassword(loggedUser.getPassword())
                        .firstName(req.getParameter("firstName"))
                        .lastName(req.getParameter("lastName"))
                        .birthday(req.getParameter("birthday"))
                        .country(loggedUser.getCountry().getCode())
                        .gender(req.getParameter("gender")).build();
                formValidation = profile.validate();
        }//end of switch
        try {
            UserDao userDao = DaoFactory.getFactory().getUserDao();
            if (formValidation.isValid()) {
                userDao.updateById(loggedUser.getId(), profile);
                log.info("Profile of [" + loggedUser.getUsername() + "](id:[" + loggedUser.getId() + "]) updated");
                req.getSession().setAttribute("loggedUser", userDao.getById(loggedUser.getId()));
                req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
            } else {
                req.setAttribute("formValidation", formValidation);
                req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
            }
        } catch (DaoException e) {
            log.error("Error when updating profile of [" + loggedUser.getUsername() + "](id:[" + loggedUser.getId() + "])", e);
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
