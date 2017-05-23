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

        assertEquals(Relation.BAD, nz.checkRelation(ru));
        assertEquals(Relation.BAD, ru.checkRelation(nz));
        assertEquals(Relation.GOOD, nz.checkRelation(jp));
        assertEquals(Relation.NEUTRAL, ru.checkRelation(jp));
        assertEquals(Relation.SAME, ru.checkRelation(ru2));
    }
}