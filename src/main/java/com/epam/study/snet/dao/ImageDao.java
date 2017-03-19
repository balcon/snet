package com.epam.study.snet.dao;

import com.epam.study.snet.model.Image;

import java.io.InputStream;

public interface ImageDao {
    Image create(InputStream image) throws DaoException;

    byte[] getById(Image image) throws DaoException;
}
