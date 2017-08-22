package com.epam.study.snet.model.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class RelationTest {
    @Test
    public void testParsing() throws Exception {
        Relation bad=Relation.parse("bad");
        Relation good=Relation.parse("GOOD");

        assertTrue(Relation.BAD==bad);
        assertTrue(Relation.GOOD==good);
    }
}