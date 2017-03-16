package com.epam.study.snet.model;

import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.enums.Gender;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public class RegistrationFields {
    String username;
    String password;
    String confirmPassword;
    String firstName;
    String lastName;
    String gender;

    public Map<String, FormErrors> validate() {
        Map<String, FormErrors> errors = new HashMap<>();
        if (username.isEmpty()) errors.put("username", FormErrors.field_empty);
        if (password.length() < 6) errors.put("password", FormErrors.password_short6);
        if (password.isEmpty()) errors.put("password", FormErrors.field_empty);
        if (confirmPassword.isEmpty()) errors.put("confirmPassword", FormErrors.field_empty);
        if (firstName.isEmpty()) errors.put("firstName", FormErrors.field_empty);
        if (lastName.isEmpty()) errors.put("lastName", FormErrors.field_empty);
        if (gender.isEmpty()) errors.put("gender", FormErrors.field_empty);
        return errors;
    }

    public User toUser() {
        Gender gender = this.gender.equals("male") ? Gender.MALE : Gender.FEMALE;
        User user = User.builder()
                .username(username)
                .password(DigestUtils.md5Hex(password))
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender).build();
        return user;
    }
}
