package com.epam.study.snet.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/main/*")
public class RedirectToLogin implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getSession().getAttribute("loggedUser") == null) {
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/login");
        } else
            chain.doFilter(request, response);

    }

    public void destroy() {

    }
}
