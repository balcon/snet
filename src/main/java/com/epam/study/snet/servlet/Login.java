package com.epam.study.snet.servlet;

import com.epam.study.snet.FormErrors;
import com.epam.study.snet.dao.db.DbConfig;
import com.epam.study.snet.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Map<String, FormErrors> errors = validate(username, password);
        User user = DbConfig.daoFactory.getUserDao().getByUsername(username);
        if (errors.isEmpty()) {
            req.getSession().setAttribute("user", user);
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/main");
        } else {
            req.setAttribute("validation", errors);
            req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
        }
    }

    private Map<String, FormErrors> validate(String username, String password) {
        Map<String, FormErrors> errors = new HashMap<>();
        User user = DbConfig.daoFactory.getUserDao().getByUsername(username);
        String passHash = DigestUtils.md5Hex(password);
        if (user == null) errors.put("loginForm", FormErrors.bad_login_password);
        else if (!user.getPassHash().equals(passHash)) errors.put("loginForm", FormErrors.bad_login_password);
        if (username.isEmpty()) errors.put("username", FormErrors.field_empty);
        if (password.isEmpty()) errors.put("password", FormErrors.field_empty);
        return errors;
    }
}