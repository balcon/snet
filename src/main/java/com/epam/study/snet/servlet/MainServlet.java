package com.epam.study.snet.servlet;

import com.epam.study.snet.model.Countries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/main")
public class MainServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale=(Locale)req.getSession().getAttribute("locale");
        req.setAttribute("countries", new Countries(locale));
        req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
    }
}
