package com.epam.study.snet.model.dao;

import com.epam.study.snet.model.entity.Country;
import com.epam.study.snet.model.enums.Relation;
import com.epam.study.snet.controller.services.RelationManager;

public interface RelationshipDao {
    void setRelation(Country country1, Country country2, Relation relation) throws DaoException;

    Relation getRelation(Country country1, Country country2) throws DaoException;

    RelationManager getRelationManager() throws DaoException;
}
