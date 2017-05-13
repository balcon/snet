package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main/removeUser")
public class RemoveUserServlet extends HttpServlet {
    private static Logger log=Logger.getLogger(RemoveUserServlet.class.getCanonicalName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        try {
            DaoFactory.getFactory().getUserDao().removeById(loggedUser.getId());
            req.getSession().setAttribute("loggedUser",null);

            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath+"/main");
        } catch (DaoException e) {
            log.error("["+loggedUser.getUsername()+"](id:["+loggedUser.getId()+"]) try to remove profile",e);
            req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
        }
    }
}
