package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.ProfileFields;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/main/profile")
public class ProfileServlet extends HttpServlet {
    static final Logger log= Logger.getLogger(ProfileServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        ProfileFields profile;
        if (req.getParameter("changed").equals("pass")) {
            profile = ProfileFields.builder()
                    .username(loggedUser.getUsername())
                    .password(req.getParameter("password"))
                    .confirmPassword(req.getParameter("confirmPassword"))
                    .firstName(loggedUser.getFirstName())
                    .lastName(loggedUser.getLastName())
                    .birthday(loggedUser.getBirthday().toString())
                    .gender(loggedUser.getGender().toString()).build();
        } else profile = ProfileFields.builder()
                .username(loggedUser.getUsername())
                //todo make something with hash
                .password(loggedUser.getPassword())
                .confirmPassword(loggedUser.getPassword())
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .birthday(req.getParameter("birthday"))
                .gender(req.getParameter("gender")).build();

        Map<String, FormErrors> validation = profile.validate();
        try {
            UserDao userDao = DaoFactory.getFactory().getUserDao();
            if (validation.isEmpty()) {
                userDao.updateById(loggedUser.getId(), profile);
                req.getSession().setAttribute("loggedUser", userDao.getById(loggedUser.getId()));
                req.setAttribute("changed", req.getParameter("changed"));
                req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
            } else {
                req.setAttribute("validation", validation);
                req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);

            }
        } catch (DaoException e) {
            log.error("["+loggedUser.getUsername()+"](id:["+loggedUser.getId()+"]) try upgrade profile",e);
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
