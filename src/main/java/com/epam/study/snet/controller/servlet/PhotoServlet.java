package com.epam.study.snet.controller.servlet;

import com.epam.study.snet.model.dao.DaoFactory;
import com.epam.study.snet.model.dao.ImageDao;
import com.epam.study.snet.model.dao.StatusMessageDao;
import com.epam.study.snet.model.dao.UserDao;
import com.epam.study.snet.model.entity.User;
import com.epam.study.snet.view.Image;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/main/image")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB //TODO getById about this
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class PhotoServlet extends HttpServlet {


    //TODO make rewrite image. Not add new
//TODO sneakyThrows!
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ImageDao imageDao = DaoFactory.getFactory().getImageDao();
        byte[] imageBytes;
        long imageId = Long.valueOf(req.getParameter("imageId"));
        imageBytes = imageDao.getById(imageId);
        resp.setContentType("imageBytes/jpg");
        resp.getOutputStream().write(imageBytes);

    }

    //TODO sneakyThrows!
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ImageDao imageDao = DaoFactory.getFactory().getImageDao();
        Part imagePart = req.getPart("imageFile");
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        Image image = null;
        image = imageDao.create(imagePart.getInputStream());
        //TODO make better
        User user = User.builder().id(loggedUser.getId()).photo(image).build();
        StatusMessageDao statusMessageDao=DaoFactory.getFactory().getStatusMessageDao();
        UserDao userDao = DaoFactory.getFactory().getUserDao(statusMessageDao);
        userDao.update(user);
        req.getSession().setAttribute("loggedUser", userDao.getById(loggedUser.getId()));
        String contextPath = req.getContextPath();
        resp.sendRedirect(contextPath + "/main/profile");
    }
}
