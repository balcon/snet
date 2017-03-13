package com.epam.study.snet.servlet;

import com.epam.study.snet.FormErrors;
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
        Map<String, FormErrors> errors = validate(username, password, firstName, lastName);
        if (errors.isEmpty()) {
            DbConfig.daoFactory.getUserDao().create(username, DigestUtils.md5Hex(password),firstName,lastName);
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/login");
        } else {
            req.setAttribute("validation", errors);
            req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
        }
    }

    private Map<String, FormErrors> validate(String username, String password, String firstName, String lastName) {
        Map<String, FormErrors> errors = new HashMap<>();
        if (DbConfig.daoFactory.getUserDao().getByUsername(username) != null)
            errors.put("username", FormErrors.username_exists);
        if(username.isEmpty()) errors.put("username", FormErrors.field_empty);
        if (password.length() < 6) errors.put("password", FormErrors.password_short6);
        if(password.isEmpty()) errors.put("password", FormErrors.field_empty);
        if (firstName.isEmpty()) errors.put("firstName", FormErrors.field_empty);
        if (lastName.isEmpty()) errors.put("lastName", FormErrors.field_empty);
        return errors;
    }
}
