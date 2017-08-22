package com.epam.study.snet.view;

import com.epam.study.snet.model.dao.DaoException;
import com.epam.study.snet.model.dao.DaoFactory;
import com.epam.study.snet.model.dao.StatusMessageDao;
import com.epam.study.snet.model.dao.UserDao;
import com.epam.study.snet.model.entity.Country;
import com.epam.study.snet.model.entity.User;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;

@Value
public class People {
    final int LIMIT = 10;
    @NonFinal
    List<User> users;
    List<Country> countries;

    long numberPages;
    long activePage;

    public People(User loggedUser, String page, String countryCode) throws DaoException {
        StatusMessageDao statusMessageDao=DaoFactory.getFactory().getStatusMessageDao();
        UserDao userDao = DaoFactory.getFactory().getUserDao(statusMessageDao);
        if (countryCode != null) {
            Country country=new Country(countryCode);
            long numberUsers=userDao.getNumber(loggedUser,country);
            numberPages=(numberUsers-1)/LIMIT+1;
            if (page != null) {
                activePage = Integer.valueOf(page);
                if (activePage > 0)
                    users = userDao.getList(loggedUser, country, (activePage - 1) * LIMIT, LIMIT);
                else users = userDao.getList(loggedUser,country);
            } else {
                activePage = 1;
                users = userDao.getList(loggedUser, country, (activePage - 1) * LIMIT, LIMIT);
            }
        } else {
            long numberUsers = userDao.getNumber() - 1;
            numberPages = (numberUsers - 1) / LIMIT + 1;
            if (page != null) {
                activePage = Integer.valueOf(page);
                if (activePage > 0)
                    users = userDao.getList(loggedUser, (activePage - 1) * LIMIT, LIMIT);
                else users = userDao.getList(loggedUser);
            } else {
                activePage = 1;
                users = userDao.getList(loggedUser, (activePage - 1) * LIMIT, LIMIT);
            }
        }
        countries = userDao.getCountries(loggedUser);
    }
}
