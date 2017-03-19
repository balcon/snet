package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.dao.ImageDao;
import com.epam.study.snet.model.Image;
import com.epam.study.snet.model.User;
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
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ImageServlet extends HttpServlet {
    private ImageDao imageDao = DaoConfig.daoFactory.getImageDao();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] imageBytes;
        long imageId=Long.getLong(req.getParameter("imageId"));
        Image image=Image.builder().id(imageId).build();
        imageBytes = imageDao.getById(image);
        resp.setContentType("imageBytes/jpg");
        resp.getOutputStream().write(imageBytes);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part imagePart = req.getPart("imageFile");
        User currentUser=(User)req.getSession().getAttribute("user");
        Image image=null;
        image = imageDao.create(imagePart.getInputStream());
        User user=User.builder().id(currentUser.getId()).photo(image).build();
        DaoConfig.daoFactory.getUserDao().update(user);
        String contextPath = req.getContextPath();
        resp.sendRedirect(contextPath + "/main/profile");
    }
}
