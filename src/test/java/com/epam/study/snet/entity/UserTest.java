package com.epam.study.snet.entity;

import com.epam.study.snet.dao.RelationshipDao;
import com.epam.study.snet.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.enums.Relation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest extends MySqlDaoTests {

    private RelationshipDao relationshipDao = daoFactory.getRelationshipDao();

//    @Test
//    public void checkRelation() throws Exception {
//
//        Country jp = new Country("JP");
//        Country us = new Country("US");
//        relationshipDao.setRelation(jp, us, Relation.BAD);
//        User user1 = User.builder().firstName("user1").country(us).build();
//        User user2 = User.builder().firstName("user2").country(jp).build();
//
//        assertEquals(Relation.BAD,user1.checkRelation(user2));
//    }
}