package com.epam.study.snet.model.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenderTest {
    @Test
    public void testParsing() throws Exception {
        Gender male=Gender.parse("male");
        Gender female=Gender.parse("FEMALE");

        assertTrue(male==Gender.MALE);
        assertTrue(female==Gender.FEMALE);
    }
}