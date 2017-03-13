package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.db.DbConfig;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registration")
public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Map<String, String> errors = validate(username, password, firstName, lastName);
        if (errors.isEmpty()) {
            DbConfig.daoFactory.getUserDao().create(firstName, lastName, username, DigestUtils.md5Hex(password));
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/login");
        } else {
            req.setAttribute("validation", errors);
            req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
        }
    }

    Map<String, String> validate(String username, String password, String firstName, String lastName) {
        Map<String, String> errors = new HashMap<>();
        if (DbConfig.daoFactory.getUserDao().getByUsername(username) != null)
            errors.put("username", "username.exist");
        if (username.length() <= 6) errors.put("username", "username.short");
        if (password.length() <= 6) errors.put("password", "password.short");
        if (firstName.isEmpty()) errors.put("firstName", "field.empty");
        if (lastName.isEmpty()) errors.put("lastName", "field.empty");
        return errors;
    }
}
