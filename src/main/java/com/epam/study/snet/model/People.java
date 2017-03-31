package com.epam.study.snet.model;

import com.epam.study.snet.dao.DaoConfig;
import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.UserDao;
import lombok.Value;

import java.util.List;

@Value
public class People {
    final int LIMIT = 10;
    List<User> users;

    long numberPages;
    long activePage;
    public People(User loggedUser, String page) throws DaoException {
        UserDao userDao = DaoConfig.daoFactory.getUserDao();
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