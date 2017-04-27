package com.epam.study.snet.model;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.entity.User;
import lombok.Value;

import java.util.List;

@Value
public class People {
    final int LIMIT = 10;
    List<User> users;

    long numberPages;
    long activePage;
    public People(User loggedUser, String page) throws DaoException {
        UserDao userDao = DaoFactory.getFactory().getUserDao();
        long numberUsers = userDao.getNumber() - 1;
        numberPages = (numberUsers - 1) / LIMIT + 1;
        if (page != null) {
            activePage = Integer.valueOf(page);
            if (activePage > 0)
               users=userDao.getList(loggedUser,(activePage-1)*LIMIT,LIMIT);
            else
                users=userDao.getList(loggedUser);
        } else {
            activePage = 1;
            users=userDao.getList(loggedUser,(activePage-1)*LIMIT,LIMIT);
        }
    }
}
