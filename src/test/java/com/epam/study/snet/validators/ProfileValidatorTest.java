package com.epam.study.snet.validators;

import com.epam.study.snet.dao.StatusMessageDao;
import com.epam.study.snet.dao.db.mysql.MySqlDaoTests;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.FormValidation;
import com.epam.study.snet.model.HashPass;
import com.epam.study.snet.validators.ProfileValidator;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileValidatorTest extends MySqlDaoTests{

    @Test
    public void correctInputs() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("juser12345")
                .password("user.password")
                .confirmPassword("user.password")
                .firstName("John")
                .lastName("Smith")
                .gender("male")
                .country("RU")
                .birthday(LocalDate.now().toString())
                .gender("MALE").build();
        FormValidation validate = profile.validate();

        assertTrue(validate.isValid());
    }

    @Test
    public void someFieldsIsEmpty() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("")
                .password("user.password")
                .confirmPassword("user.password")
                .firstName("John")
                .lastName("Smith").build();
        FormValidation validate = profile.validate();

        assertTrue(validate.getErrors().containsKey("username"));
        assertTrue(validate.getErrors().containsKey("gender"));
        assertTrue(validate.getErrors().containsKey("country"));
        assertFalse(validate.getErrors().containsKey("firstName"));
    }

    @Test
    public void usernameOnlyLatinCharactersAndNumbers() throws Exception {
        ProfileValidator prof1 = ProfileValidator.builder()
                .username("Use*r").build();
        ProfileValidator prof2 = ProfileValidator.builder()
                .username("кириллица").build();
        ProfileValidator prof3 = ProfileValidator.builder()
                .username("user_xp-89").build();

        assertTrue(prof1.validate().getErrors().get("username") == FormErrors.username_incorrect);
        assertTrue(prof2.validate().getErrors().get("username") == FormErrors.username_incorrect);
        assertFalse(prof3.validate().getErrors().get("username") == FormErrors.username_incorrect);
    }

    @Test
    public void passNotEqualsWithConfirm() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("juser")
                .password("user.password")
                .confirmPassword("anotherPassword")
                .firstName("John")
                .lastName("Smith")
                .gender("male").build();
        FormValidation validate = profile.validate();

        assertTrue(validate.getErrors().get("password") == FormErrors.password_not_equals_confirm);
        assertTrue(validate.getErrors().get("confirmPassword") == FormErrors.password_not_equals_confirm);
    }

    @Test
    public void avoidNPE() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username(null)
                .gender(null).build();
        FormValidation validate = profile.validate();

        assertTrue(validate.getErrors().get("username") == FormErrors.field_empty);
    }

    @Test
    public void hashPass() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("user")
                .password("password").build();
        HashPass hasher = new HashPass();

        profile.hashPass(hasher);

        assertEquals(hasher.getHash("password"), profile.getPassword());
    }

    @Test
    public void usernameExists() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("juser")
                .password("password")
                .confirmPassword("password")
                .firstName("John")
                .lastName("Smith")
                .gender("male")
                .birthday(LocalDate.now().toString()).build();
        StatusMessageDao statusMessageDao=daoFactory.getStatusMessageDao();
        daoFactory.getUserDao(statusMessageDao).create(profile);
        FormValidation formValidation = profile.checkUsername();

        assertTrue(formValidation.getErrors().get("username")==FormErrors.username_exists);
    }
}