package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.ProfileValidator;
import com.epam.study.snet.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/registration")

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                .gender(req.getParameter("gender")).build();


        Map<String, FormErrors> validation = profile.validate();

        try {
            UserDao userDao = DaoFactory.getFactory().getUserDao();
            if (validation.isEmpty()) {
                if (userDao.getByUsername(profile.getUsername()) == null) {
                    User user = profile.toUser();
                    user.setPassword(DigestUtils.md5Hex(user.getPassword()));
                    userDao.create(user);
                    resp.sendRedirect(req.getContextPath() + "/login");
                } else {
                    validation.put("username", FormErrors.username_exists);
                    req.setAttribute("validation", validation);
                    req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
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
