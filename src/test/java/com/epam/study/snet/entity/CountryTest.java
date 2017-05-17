package com.epam.study.snet.entity;

import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.enums.Relation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountryTest extends MySqlDaoTests {

    private RelationshipDao relationshipDao = daoFactory.getRelationshipDao();

    @Test
    public void checkRelation() throws Exception {

        Country nz = new Country("NZ");
        Country ru = new Country("RU");
        Country ru2 = new Country("RU");
        Country jp = new Country("JP");

        relationshipDao.setRelation(nz, ru, Relation.BAD);
        relationshipDao.setRelation(nz, jp, Relation.GOOD);

        assertEquals(nz.checkRelation(ru), Relation.BAD);
        assertEquals(ru.checkRelation(nz), Relation.BAD);
        assertEquals(nz.checkRelation(jp), Relation.GOOD);
        assertEquals(ru.checkRelation(jp), Relation.NEUTRAL);
        assertEquals(ru.checkRelation(ru2), Relation.SAME);
    }
}