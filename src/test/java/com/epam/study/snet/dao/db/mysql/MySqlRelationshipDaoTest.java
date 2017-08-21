package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.entity.Country;
import com.epam.study.snet.enums.Relation;
import com.epam.study.snet.model.RelationManager;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MySqlRelationshipDaoTest extends MySqlDaoTests {
    private RelationshipDao relationshipDao = daoFactory.getRelationshipDao();
    private Country us = new Country("US");
    private Country ru = new Country("RU");
    private Country gb = new Country("GB");
    private Country nz = new Country("NZ");
    private Country kz = new Country("KZ");

    private Country c1=new Country("C1");
    private Country c2=new Country("C2");
    private Country c3=new Country("C3");

    public MySqlRelationshipDaoTest() throws DaoException {
    }

    @Test
    public void setAndGetRelation() throws Exception {
        relationshipDao.setRelation(ru, us, Relation.BAD);

        Relation relation = relationshipDao.getRelation(us, ru);

        assertEquals(Relation.BAD, relation);
    }

    @Test
    public void resetRelation() throws Exception {
        relationshipDao.setRelation(ru, gb, Relation.BAD);
        relationshipDao.setRelation(gb, ru, Relation.GOOD);
        Relation relation = relationshipDao.getRelation(ru, gb);

        assertEquals(Relation.GOOD, relation);
    }

    @Test
    public void resetToNeutral() throws Exception {
        relationshipDao.setRelation(ru, kz, Relation.GOOD);
        relationshipDao.setRelation(kz, ru, Relation.NEUTRAL);

        Relation relation = relationshipDao.getRelation(ru, kz);

        assertEquals(Relation.NEUTRAL, relation);
    }

    @Test
    public void getRelations() throws Exception {
        relationshipDao.setRelation(ru, gb, Relation.BAD);
        relationshipDao.setRelation(kz, ru, Relation.GOOD);

        Map<Country,Relation> relations=relationshipDao.getRelations(ru);

        assertEquals(Relation.BAD,relations.get(gb));
        assertEquals(Relation.GOOD,relations.get(kz));
    }

    @Test
    public void getRelationshipManagerTest() throws Exception {
        RelationManager relationManager = relationshipDao.getRelationManager();

        assertTrue(relationManager !=null);
    }
}