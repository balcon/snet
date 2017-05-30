package com.epam.study.snet.validators;

import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.HashPass;
import com.epam.study.snet.validators.ProfileValidator;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileValidatorTest {

    @Test
    public void correctInputs() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("juser")
                .password("user.password")
                .confirmPassword("user.password")
                .firstName("John")
                .lastName("Smith")
                .gender("male")
                .country("RU")
                .birthday(LocalDate.now().toString())
                .gender("MALE").build();
        Map<String, FormErrors> validate = profile.validate();

        assertTrue(validate.isEmpty());
    }

    @Test
    public void someFieldsIsEmpty() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username("")
                .password("user.password")
                .confirmPassword("user.password")
                .firstName("John")
                .lastName("Smith").build();
        Map<String, FormErrors> validate = profile.validate();

        assertTrue(validate.containsKey("username"));
        assertTrue(validate.containsKey("gender"));
        assertTrue(validate.containsKey("country"));
        assertFalse(validate.containsKey("firstName"));
    }

    @Test
    public void usernameOnlyLatinCharactersAndNumbers() throws Exception {
        ProfileValidator prof1 = ProfileValidator.builder()
                .username("Use*r").build();
        ProfileValidator prof2 = ProfileValidator.builder()
                .username("кириллица").build();
        ProfileValidator prof3 = ProfileValidator.builder()
                .username("user_xp-89").build();

        assertTrue(prof1.validate().get("username") == FormErrors.username_incorrect);
        assertTrue(prof2.validate().get("username") == FormErrors.username_incorrect);
        assertFalse(prof3.validate().get("username") == FormErrors.username_incorrect);
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
        Map<String, FormErrors> validate = profile.validate();

        assertTrue(validate.get("password") == FormErrors.password_not_equals_confirm);
        assertTrue(validate.get("confirmPassword") == FormErrors.password_not_equals_confirm);
    }

    @Test
    public void avoidNPE() throws Exception {
        ProfileValidator profile = ProfileValidator.builder()
                .username(null)
                .gender(null).build();
        Map<String, FormErrors> validate = profile.validate();

        assertTrue(validate.get("username") == FormErrors.field_empty);
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
}