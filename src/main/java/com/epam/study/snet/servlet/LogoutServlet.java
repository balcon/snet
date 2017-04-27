package com.epam.study.snet.servlet;

import com.epam.study.snet.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/main/logout")
public class LogoutServlet extends HttpServlet {
    private static Logger log=Logger.getLogger(LogoutServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user=(User)session.getAttribute("loggedUser");
        log.info("User ["+user.getUsername()+"],id["+user.getId()+"] is logged out");
        session.setAttribute("loggedUser",null);
        String contextPath = req.getContextPath();
        resp.sendRedirect(contextPath+"/login");
    }
}
