package com.epam.study.snet.servlet;

import com.epam.study.snet.Static;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("firstName").isEmpty()) {
            req.setAttribute("fncorrect", false);
            req.getRequestDispatcher("registration").forward(req, resp);
        } else {
            Static.daoFactory.getUserDao()
                    .create(req.getParameter("firstName"),
                            req.getParameter("lastName"),
                            req.getParameter("username"),
                            DigestUtils.md5Hex(req.getParameter("password")));

            resp.sendRedirect("login");
        }
    }
}
