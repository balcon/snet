package com.epam.study.snet.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HashPassTest {

    private HashPass hashPass = new HashPass();

    @Test
    public void samePass() throws Exception {
        String pass1="abc";
        String pass2="abc";

        String hash1=hashPass.getHash(pass1);
        String hash2=hashPass.getHash(pass2);

        assertTrue(hash1.equals(hash2));
    }

    @Test
    public void differentPass() throws Exception {
        String pass1="abcdefg";
        String pass2="Abcdefg";

        String hash1=hashPass.getHash(pass1);
        String hash2=hashPass.getHash(pass2);

        assertFalse(hash1.equals(hash2));
    }
}