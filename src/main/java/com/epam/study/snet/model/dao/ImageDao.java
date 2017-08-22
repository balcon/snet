package com.epam.study.snet.model.dao;

import com.epam.study.snet.view.Image;

import java.io.InputStream;

public interface ImageDao{
    Image create(InputStream image) throws DaoException;

    byte[] getById(long id) throws DaoException;

    void removeById(long id) throws DaoException;
}
