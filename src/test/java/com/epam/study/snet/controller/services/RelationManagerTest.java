package com.epam.study.snet.controller.services;

import com.epam.study.snet.model.dao.RelationshipDao;
import com.epam.study.snet.model.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.model.entity.Country;
import com.epam.study.snet.model.enums.Relation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RelationManagerTest extends MySqlDaoTests {

    private RelationshipDao relationshipDao = daoFactory.getRelationshipDao();

    Country us = new Country("US");
    Country ru = new Country("RU");
    Country gb = new Country("GB");
    Country ua = new Country("UA");

    @Before
    public void setUp() throws Exception {
        relationshipDao.setRelation(us, ru, Relation.BAD);
        relationshipDao.setRelation(us, gb, Relation.GOOD);
        relationshipDao.setRelation(ru, ua, Relation.BAD);
        relationshipDao.setRelation(ru, gb, Relation.GOOD);
    }

    @Test
    public void checkRelation() throws Exception {
        RelationManager relationManager = relationshipDao.getRelationManager();

        assertEquals(Relation.NEUTRAL, relationManager.checkRelation(gb, ua));
        assertEquals(Relation.BAD, relationManager.checkRelation(us, ru));
        assertEquals(Relation.GOOD, relationManager.checkRelation(gb, us));
        assertEquals(Relation.SAME, relationManager.checkRelation(gb, gb));
    }

    @Test
    public void getBadRelationsList() throws Exception {
        RelationManager relationManager = relationshipDao.getRelationManager();
        List<Country> badRelations = relationManager.getBadRelations(ru);
        assertEquals(2, badRelations.size());
    }

    @Test
    public void getGoodRelationsList() throws Exception {
        RelationManager relationManager = relationshipDao.getRelationManager();
        List<Country> badRelations = relationManager.getGoodRelations(gb);
        assertEquals(2, badRelations.size());
    }
}