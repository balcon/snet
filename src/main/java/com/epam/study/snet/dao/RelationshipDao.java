package com.epam.study.snet.dao;

import com.epam.study.snet.enums.Relation;

public interface RelationshipDao {
    Relation getRelation(String country1, String country2) throws DaoException;

    void setRelation(String country1, String country2, Relation relation) throws DaoException;
}
