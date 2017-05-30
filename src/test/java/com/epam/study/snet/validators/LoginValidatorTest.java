package com.epam.study.snet.validators;

import com.epam.study.snet.dao.UserDao;
import com.epam.study.snet.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.enums.FormErrors;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class LoginValidatorTest{
//    @Test
//    public void correctInputs() throws Exception {
//        LoginValidator login = LoginValidator.builder()
//                .username("User")
//                .password("Password").build();
//        Map<String, FormErrors> validation = login.validate();
//
//        assertTrue(validation.isEmpty());
//    }
//    @Test
//    public void incorrectInputs() throws Exception {
//        LoginValidator login= LoginValidator.builder()
//                .username("").build();
//        Map<String, FormErrors> validation= login.validate();
//
//        assertTrue(validation.containsKey("username"));
//        assertTrue(validation.containsKey("password"));
//    }
}