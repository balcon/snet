package com.epam.study.snet.model;

import com.epam.study.snet.enums.FormErrors;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class LoginFieldsTest {

    @Test
    public void correctInputs() throws Exception {
        LoginValidator fields = LoginValidator.builder()
                .username("User")
                .password("Password").build();
        Map<String, FormErrors> validation = fields.validate();

        assertTrue(validation.isEmpty());
    }

    @Test
    public void incorrectInputs() throws Exception {
        LoginValidator fields= LoginValidator.builder()
                .username("").build();
        Map<String, FormErrors> validation= fields.validate();

        assertTrue(validation.containsKey("username"));
        assertTrue(validation.containsKey("password"));
    }
}