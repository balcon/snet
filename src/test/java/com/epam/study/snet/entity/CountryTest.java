package com.epam.study.snet.entity;

import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.enums.Relation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CountryTest extends MySqlDaoTests {

    private RelationshipDao relationshipDao = daoFactory.getRelationshipDao();

//    private Country nz;
//    private Country ru;
//    private Country ru2;
//    private Country jp;
//
//    private Country c1;
//    private Country c2;
//    private Country c3;
//
//    @Before
//    public void setUp() throws Exception {
//        Country nz = new Country("NZ");
//        Country ru = new Country("RU");
//        Country ru2 = new Country("RU");
//        Country jp = new Country("JP");
//        Country c1 = new Country("C1");
//
//        Country c2 = new Country("C2");
//        Country c3 = new Country("C3");
//    }
//
//    @Test
//    public void checkRelation() throws Exception {
//        relationshipDao.setRelation(nz, ru, Relation.BAD);
//        relationshipDao.setRelation(nz, jp, Relation.GOOD);
//
//        assertEquals(Relation.BAD, nz.checkRelation(ru));
//        assertEquals(Relation.BAD, ru.checkRelation(nz));
//        assertEquals(Relation.GOOD, nz.checkRelation(jp));
//        assertEquals(Relation.NEUTRAL, ru.checkRelation(jp));
//        assertEquals(Relation.SAME, ru.checkRelation(ru2));
//    }
//
//    @Test
//    public void getListRelations() throws Exception {
//        relationshipDao.setRelation(c1, c2, Relation.BAD);
//        relationshipDao.setRelation(c3, c1, Relation.BAD);
//        relationshipDao.setRelation(c2, c3, Relation.GOOD);
//
//        Set<Country> listBadRelations = c1.getBadRelations();
//        Set<Country> listGoodRelations = c2.getGoodRelations();
//
//        assertEquals(2, listBadRelations.size());
//        assertEquals(1, listGoodRelations.size());
//    }
}