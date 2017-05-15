package com.epam.study.snet.dao.db.mysql;


import com.epam.study.snet.enums.Relation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MySqlRelationshipDaoTest extends MySqlDaoTests {
    @Test
    public void setAndGetRelation() throws Exception {
        relationshipDao.setRelation("RU","US",Relation.BAD);

        Relation relation = relationshipDao.getRelation("US", "RU");

        assertEquals(relation,Relation.BAD);
    }

    @Test
    public void resetRelation() throws Exception {
        relationshipDao.setRelation("RU","GB",Relation.BAD);
        relationshipDao.setRelation("GB","RU",Relation.GOOD);
        Relation relation=relationshipDao.getRelation("RU","GB");

        assertEquals(relation,Relation.GOOD);
    }

    @Test
    public void getNeutralRelation() throws Exception {
        Relation relation=relationshipDao.getRelation("RU","NZ");

        assertEquals(relation,Relation.NEUTRAL);
    }

    @Test
    public void resetToNeutral() throws Exception {
        relationshipDao.setRelation("RU","KZ",Relation.GOOD);
        relationshipDao.setRelation("KZ","RU",Relation.NEUTRAL);

        Relation relation=relationshipDao.getRelation("RU","KZ");

        assertEquals(relation,Relation.NEUTRAL);
    }
}