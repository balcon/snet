package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.model.Countries;
import com.epam.study.snet.model.People;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/main/people")
public class PeopleServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(PeopleServlet.class.getCanonicalName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        try {
            People people = new People(loggedUser, req.getParameter("page"));
            String lang = ((String) req.getSession().getAttribute("locale")).substring(0, 2);
            req.setAttribute("countries", new Countries(new Locale(lang)));
            req.setAttribute("people", people);
            req.getRequestDispatcher("/WEB-INF/pages/people.jsp").forward(req, resp);
        } catch (DaoException e) {
            log.error("[" + loggedUser.getId() + "][" + loggedUser.getUsername() +
                    "] try to get users list", e);
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
