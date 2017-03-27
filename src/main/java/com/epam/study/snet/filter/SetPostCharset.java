package com.epam.study.snet.filter;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SetPostCharset implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getSession().getAttribute("user") != null) {
            User user = (User) req.getSession().getAttribute("user");
            try {
                int unreadMessages = DaoConfig.daoFactory.getMessageDao().getNumberUnread(user);
                req.getSession().setAttribute("unreadMessages", unreadMessages);
            } catch (DaoException e) {
                e.printStackTrace();
                req.getRequestDispatcher("/WEB-INF/pages/errorpage.jsp").forward(req, resp);
            }
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {

    }
}
