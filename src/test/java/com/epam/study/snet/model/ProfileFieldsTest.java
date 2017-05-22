package com.epam.study.snet.model;

import com.epam.study.snet.entity.User;
import com.epam.study.snet.enums.FormErrors;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.Assert.*;

public class ProfileFieldsTest {

    @Test
    public void correctInputs() throws Exception {
        ProfileFields fields = ProfileFields.builder()
                .username("juser")
                .password("user.password")
                .confirmPassword("user.password")
                .firstName("John")
                .lastName("Smith")
                .gender("male")
                .country("RU")
                .birthday(LocalDate.now().toString())
                .gender("MALE").build();
        Map<String, FormErrors> validate = fields.validate();

        assertTrue(validate.isEmpty());
    }

    @Test
    public void someFieldsIsEmpty() throws Exception {
        ProfileFields fields = ProfileFields.builder()
                .username("")
                .password("user.password")
                .confirmPassword("user.password")
                .firstName("John")
                .lastName("Smith").build();
        //without setGender
        Map<String, FormErrors> validate = fields.validate();

        assertTrue(validate.containsKey("username"));
        assertTrue(validate.containsKey("gender"));
        assertTrue(validate.containsKey("country"));
    }

    @Test
    public void usernameOnlyLatinCharactersAndNumbers() throws Exception {
        ProfileFields fields1 = ProfileFields.builder()
                .username("Use*r").build();
        ProfileFields fields2 = ProfileFields.builder()
                .username("кириллица").build();
        ProfileFields fields3 = ProfileFields.builder()
                .username("user_xp-89").build();

        assertTrue(fields1.validate().get("username") == FormErrors.username_incorrect);
        assertTrue(fields2.validate().get("username") == FormErrors.username_incorrect);
        assertFalse(fields3.validate().get("username") == FormErrors.username_incorrect);
    }

    @Test
    public void passNotEqualsWithConfirm() throws Exception {
        ProfileFields fields = ProfileFields.builder()
                .username("juser")
                .password("user.password")
                .confirmPassword("anotherPassword")
                .firstName("John")
                .lastName("Smith")
                .gender("male").build();
        Map<String, FormErrors> validate = fields.validate();

        assertEquals(validate.get("password"), FormErrors.password_not_equals_confirm);
        assertEquals(validate.get("confirmPassword"), FormErrors.password_not_equals_confirm);
    }

    @Test
    public void toUserCorrect() throws Exception {
        String username = "joan_smith";
        String password = "pass123";
        String firstName = "Joan";
        String lastName = "Smith";
        String birthday = "1990-10-20";
        String gender = "FEMALE";
        ProfileFields fields = ProfileFields.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .birthday(birthday)
                .gender(gender).build();
        User user = fields.toUser();

        assertEquals(user.getUsername(), username);
        assertEquals(user.getPassword(), password);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);
        assertEquals(user.getBirthday().toString(), birthday);
        assertEquals(user.getGender().toString(), gender);
    }

    @Test
    public void avoidNPE() throws Exception {
        ProfileFields fields = ProfileFields.builder()
                .username(null)
                .gender(null).build();

        fields.validate();
    }
}