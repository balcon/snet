package com.epam.study.snet.controller.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale=new Locale(req.getParameter("language"),req.getParameter("country"));
        req.getSession().setAttribute("locale", locale);
        String currentURL = req.getParameter("currentPage");
        String queryString = req.getParameter("queryString");
        if(queryString!=null) currentURL+="?"+queryString;
        resp.sendRedirect(currentURL);
    }
}
