package com.epam.study.snet.dao;

import com.epam.study.snet.entity.Country;
import com.epam.study.snet.enums.Relation;

public interface RelationshipDao {
    Relation getRelation(Country country1, Country country2) throws DaoException;

    void setRelation(Country country1, Country country2, Relation relation) throws DaoException;
}
