package com.epam.study.snet.entity;

import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.enums.Relation;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CountryTest extends MySqlDaoTests {

    private RelationshipDao relationshipDao = daoFactory.getRelationshipDao();
   private Country nz = new Country("NZ");
   private Country ru = new Country("RU");
   private Country ru2 = new Country("RU");
   private Country jp = new Country("JP");

   private Country c1=new Country("C1");
   private Country c2=new Country("C2");
   private Country c3=new Country("C3");

    @Test
    public void checkRelation() throws Exception {
        relationshipDao.setRelation(nz, ru, Relation.BAD);
        relationshipDao.setRelation(nz, jp, Relation.GOOD);

        assertEquals(Relation.BAD, nz.checkRelation(ru));
        assertEquals(Relation.BAD, ru.checkRelation(nz));
        assertEquals(Relation.GOOD, nz.checkRelation(jp));
        assertEquals(Relation.NEUTRAL, ru.checkRelation(jp));
        assertEquals(Relation.SAME, ru.checkRelation(ru2));
    }

    @Test
    public void getListRelations() throws Exception {
        relationshipDao.setRelation(c1,c2,Relation.BAD);
        relationshipDao.setRelation(c3,c1,Relation.BAD);
        relationshipDao.setRelation(c2,c3,Relation.GOOD);

        List<Country> listBadRelations = c1.getBadRelations();
        List<Country> listGoodRelations = c2.getGoodRelations();

        assertEquals(2,listBadRelations.size());
        assertEquals(1,listGoodRelations.size());
    }
}