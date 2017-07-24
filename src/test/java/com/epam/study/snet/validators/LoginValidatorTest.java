package com.epam.study.snet.validators;

import com.epam.study.snet.dao.StatusMessageDao;
import com.epam.study.snet.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.FormValidation;
import com.epam.study.snet.model.HashPass;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class LoginValidatorTest extends MySqlDaoTests {

    @Test
    public void badPassword() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("juser2")
                .password("password")
                .confirmPassword("password")
                .firstName("John")
                .lastName("Smith")
                .gender("male")
                .birthday(LocalDate.now().toString()).build();
        profile.hashPass(new HashPass());
        StatusMessageDao statusMessageDao=daoFactory.getStatusMessageDao();
        daoFactory.getUserDao(statusMessageDao).create(profile);

        LoginValidator login = LoginValidator.builder()
                .username("juser2")
                .password("incorrectPassword").build();

        FormValidation formValidation = login.validate();

        assertTrue(formValidation.getErrors().get("loginForm")== FormErrors.bad_login_password);
    }

    @Test
    public void correctInputs() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("juser")
                .password("password")
                .confirmPassword("password")
                .firstName("John")
                .lastName("Smith")
                .gender("male")
                .birthday(LocalDate.now().toString()).build();
        profile.hashPass(new HashPass());
        StatusMessageDao statusMessageDao=daoFactory.getStatusMessageDao();
        daoFactory.getUserDao(statusMessageDao).create(profile);

        LoginValidator login = LoginValidator.builder()
                .username("juser")
                .password("password").build();

        FormValidation formValidation = login.validate();

        assertTrue(formValidation.isValid());
    }

    @Test
    public void incorrectInputs() throws Exception {
        LoginValidator login = LoginValidator.builder()
                .username("").build();
        FormValidation formValidation = login.validate();

        assertTrue(formValidation.getErrors().containsKey("username"));
        assertTrue(formValidation.getErrors().containsKey("password"));
    }
}