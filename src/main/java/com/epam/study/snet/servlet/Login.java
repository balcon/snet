package com.epam.study.snet.servlet;

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

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = DbConfig.daoFactory.getUserDao().getByUsername(username);
        if (user == null) {
            PrintWriter writer = resp.getWriter();
            writer.print("Bad user");
        } else {
            String passHash = DigestUtils.md5Hex(password);
            if (!user.getPassHash().equals(passHash)) {
                PrintWriter writer = resp.getWriter();
                writer.print("Bad password");
            } else
                req.getSession().setAttribute("user",user);
                resp.sendRedirect("main");
        }
    }
}