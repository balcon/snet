package com.epam.study.snet.dao;

import com.epam.study.snet.model.Image;

import java.io.InputStream;

public interface ImageDao<T>{
    Image create(InputStream image) throws DaoException;

    T read(Image image) throws DaoException;
}
