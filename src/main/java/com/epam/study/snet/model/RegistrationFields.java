package com.epam.study.snet.model;

import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.enums.Gender;
import lombok.Builder;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Builder
public class RegistrationFields {
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String gender;

    public Map<String, FormErrors> validate() {
        Map<String, FormErrors> errors = new HashMap<>();

        if (username == null || username.isEmpty()) errors.put("username", FormErrors.field_empty);
        else if (!username.matches("[a-zA-Z\\d\\-_]*")) errors.put("username", FormErrors.username_incorrect);

        if (password == null || password.isEmpty()) errors.put("password", FormErrors.field_empty);
        else if (password.length() < 6) errors.put("password", FormErrors.password_short6);

        if (confirmPassword == null || confirmPassword.isEmpty()) errors.put("confirmPassword", FormErrors.field_empty);
        else {
            if (confirmPassword.length() < 6) errors.put("confirmPassword", FormErrors.password_short6);
            if (!confirmPassword.equals(password)) {
                errors.put("password", FormErrors.password_not_equals_confirm);
                errors.put("confirmPassword", FormErrors.password_not_equals_confirm);
            }
        }

        if (firstName == null || firstName.isEmpty()) errors.put("firstName", FormErrors.field_empty);
        if (lastName == null || lastName.isEmpty()) errors.put("lastName", FormErrors.field_empty);
        if (gender == null || gender.isEmpty()) errors.put("gender", FormErrors.field_empty);

        return errors;
    }

    public User toUser() {
        Gender gender = this.gender.equals("male") ? Gender.MALE : Gender.FEMALE;
        return User.builder()
                .username(username)
                .password(DigestUtils.md5Hex(password))
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender).build();
    }
}
