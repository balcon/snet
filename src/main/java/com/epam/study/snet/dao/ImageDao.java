package com.epam.study.snet.dao;

import com.epam.study.snet.model.Image;

import java.io.InputStream;

public interface ImageDao{
    Image create(InputStream image) throws DaoException;

    byte[] read(Image image) throws DaoException;

    void removeById(long imageId) throws DaoException;
}
