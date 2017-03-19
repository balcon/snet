package com.epam.study.snet.dao;

public interface DaoFactory {
    UserDao getUserDao();
    MessageDao getMessageDao();
    ImageDao getImageDao();
}
