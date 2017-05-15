package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.Countries;
import com.epam.study.snet.model.ProfileValidator;
import com.epam.study.snet.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/registration")

public class RegistrationServlet extends HttpServlet {
    static private Logger log=Logger.getLogger(RegistrationServlet.class.getCanonicalName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("countries",Countries.getCountries());
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


        Map<String, FormErrors> validation = profile.validate();

        try {
            UserDao userDao = DaoFactory.getFactory().getUserDao();
            if (validation.isEmpty()) {
                if (userDao.getByUsername(profile.getUsername()) == null) {
                    User user = profile.toUser();
                    user.setPassword(DigestUtils.md5Hex(user.getPassword()));
                    user=userDao.create(user);
                    log.info("Registeren new user ["+user.getUsername()+"](id:["+user.getId()+"]");
                    resp.sendRedirect(req.getContextPath() + "/login");
                } else {
                    validation.put("username", FormErrors.username_exists);
                    req.setAttribute("validation", validation);
                    req.setAttribute("countries", Countries.getCountries());
                    req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("validation", validation);
                req.setAttribute("countries", Countries.getCountries());
                req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);

            }
        } catch (DaoException e) {
            log.error("Trying to register a new user",e);
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }


}
