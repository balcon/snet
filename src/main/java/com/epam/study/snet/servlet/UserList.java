package com.epam.study.snet.servlet;

import com.epam.study.snet.Static;
import com.epam.study.snet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/main/userList")
public class UserList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = Static.daoFactory.getUserDao().getList();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/pages/list.jsp").forward(req, resp);
    }
}
