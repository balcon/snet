package com.epam.study.snet.servlet;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.RegistrationFields;
import com.epam.study.snet.model.User;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/registration")

public class Registration extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationFields registrationFields = RegistrationFields.builder()
                .username(req.getParameter("username"))
                .password(req.getParameter("password"))
                .confirmPassword(req.getParameter("confirmPassword"))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .gender(req.getParameter("gender")).build();
        Map<String, FormErrors> errors = registrationFields.validate();
        if (errors.isEmpty()) {
            User user = registrationFields.toUser();
            DaoConfig.daoFactory.getUserDao().create(user);
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("validation", errors);
            req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);

        }
//        } catch (DaoException e) {
//            e.printStackTrace();
//            req.getRequestDispatcher("/WEB-INF/pages/fatalErrorPage.jsp").forward(req, resp);
//        }
    }

 /*   if (DaoConfig.daoFactory.getUserDao().getByUsername(username) != null)
            errors.put("username", FormErrors.username_exists);*/
}
