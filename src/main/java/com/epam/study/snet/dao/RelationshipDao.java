package com.epam.study.snet.dao;

import com.epam.study.snet.entity.Country;
import com.epam.study.snet.enums.Relation;

import java.util.List;

public interface RelationshipDao {
    Relation getRelation(Country country1, Country country2) throws DaoException;

    void setRelation(Country country1, Country country2, Relation relation) throws DaoException;

    List<Country> getListRelations(Country country, Relation relation) throws DaoException;
}
