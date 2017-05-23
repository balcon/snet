package com.epam.study.snet.dao.db.mysql;

import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.entity.Country;
import com.epam.study.snet.enums.Relation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MySqlRelationshipDaoTest extends MySqlDaoTests {
    private RelationshipDao relationshipDao = daoFactory.getRelationshipDao();
    private Country us = new Country("US");
    private Country ru = new Country("RU");
    private Country gb = new Country("GB");
    private Country nz = new Country("NZ");
    private Country kz = new Country("KZ");

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
    public void getNeutralRelation() throws Exception {
        Relation relation = relationshipDao.getRelation(ru, nz);

        assertEquals(Relation.NEUTRAL, relation);
    }

    @Test
    public void resetToNeutral() throws Exception {
        relationshipDao.setRelation(ru, kz, Relation.GOOD);
        relationshipDao.setRelation(kz, ru, Relation.NEUTRAL);

        Relation relation = relationshipDao.getRelation(ru, kz);

        assertEquals(Relation.NEUTRAL, relation);
    }
}