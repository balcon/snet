package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.LoginValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(LoginServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginValidator fields = LoginValidator.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password")).build();

        Map<String, FormErrors> validation = fields.validate();
        try {
            UserDao userDao = DaoFactory.getFactory().getUserDao();
            if (validation.isEmpty()) {
                User user = userDao.getByUsername(fields.getUsername());
                String passHash = DigestUtils.md5Hex(fields.getPassword());
                if (user != null && user.getPassword().equals(passHash)) {
                    req.getSession().setAttribute("loggedUser", user);
                    log.info("["+user.getUsername()+"](id:["+user.getId()+"]) is logged in");
                    String contextPath = req.getContextPath();
                    resp.sendRedirect(contextPath + "/main");
                } else {
                    validation.put("loginForm", FormErrors.bad_login_password);
                    req.setAttribute("validation", validation);
                    req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("validation", validation);
                req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }

    private Map<String, FormErrors> validate(String username, String password) throws DaoException {
        Map<String, FormErrors> errors = new HashMap<>();
        User user = DaoFactory.getFactory().getUserDao().getByUsername(username);
        String passHash = DigestUtils.md5Hex(password);
        if (user == null) errors.put("loginForm", FormErrors.bad_login_password);
        else if (!user.getPassword().equals(passHash)) errors.put("loginForm", FormErrors.bad_login_password);
        if (username.isEmpty()) errors.put("username", FormErrors.field_empty);
        if (password.isEmpty()) errors.put("password", FormErrors.field_empty);
        return errors;
    }
}