package com.epam.study.snet.controller.validators;

import com.epam.study.snet.view.Enums.FormErrors;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class MessageValidatorTest {
    @Test
    public void correctFields() throws Exception {
        MessageValidator message=MessageValidator.builder()
                .body("text message").build();
        Map<String, FormErrors> errors = message.validate();

        assertTrue(errors.isEmpty());
    }

    @Test
    public void messageBodyEmpty() throws Exception {
        MessageValidator message=MessageValidator.builder()
                .body("").build();
        Map<String, FormErrors> errors = message.validate();

        assertTrue(errors.get("body")==FormErrors.field_empty);
    }

    @Test
    public void messageBodyVeryLong() throws Exception {
        MessageValidator message=MessageValidator.builder()
                .body("12345678901234567890123456789012345678901234567890" +
                        "12345678901234567890123456789012345678901234567890" +
                        "12345678901234567890123456789012345678901234567890" +
                        "1234567890").build();//more than 150 chars
        Map<String, FormErrors> errors = message.validate();

        assertTrue(errors.get("body")==FormErrors.longer_than_150);
    }
}