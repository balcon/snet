package com.epam.study.snet.controller.validators;

import com.epam.study.snet.model.dao.DaoException;
import com.epam.study.snet.model.dao.DaoFactory;
import com.epam.study.snet.model.dao.StatusMessageDao;
import com.epam.study.snet.model.dao.UserDao;
import com.epam.study.snet.view.Enums.FormErrors;
import com.epam.study.snet.controller.services.HashPass;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ProfileValidator} class is a 'layer' between users input
 * and applications business logic. This class validates input data
 * of user profile
 */
@Value
@Builder
public class ProfileValidator {
    String username;
    @NonFinal
    String password;
    String confirmPassword;
    String firstName;
    String lastName;
    String birthday;
    String gender;
    String country;

    /**
     * Method validate input fields.
     *
     * @return Object of {@link FormValidation} class.
     */
    public FormValidation validate() {
        Map<String, FormErrors> errors = new HashMap<>();

        if (username == null || username.isEmpty()) errors.put("username", FormErrors.field_empty);
        else if (!username.matches("[a-zA-Z\\d\\-_]*")) errors.put("username", FormErrors.username_incorrect);
        if (password == null || password.isEmpty()) errors.put("password", FormErrors.field_empty);
        else if (password.length() < 6) errors.put("password", FormErrors.password_short6);

        if (confirmPassword == null || confirmPassword.isEmpty())
            errors.put("confirmPassword", FormErrors.field_empty);
        else {
            if (confirmPassword.length() < 6) errors.put("confirmPassword", FormErrors.password_short6);
            if (!confirmPassword.equals(password)) {
                errors.put("password", FormErrors.password_not_equals_confirm);
                errors.put("confirmPassword", FormErrors.password_not_equals_confirm);
            }
        }

        if (firstName == null || firstName.isEmpty()) errors.put("firstName", FormErrors.field_empty);
        if (lastName == null || lastName.isEmpty()) errors.put("lastName", FormErrors.field_empty);
        if (birthday == null || birthday.isEmpty()) errors.put("birthday", FormErrors.field_empty);
        if (gender == null || gender.isEmpty()) errors.put("gender", FormErrors.field_empty);
        if (country == null || country.isEmpty()) errors.put("country", FormErrors.field_empty);

        return new FormValidation(errors);
    }

    /**Checking existence of username
     *
     * @return Object of {@link FormValidation} class.
     * @throws DaoException
     */
    public FormValidation checkUsername() throws DaoException {
        Map<String, FormErrors> errors = new HashMap<>();
        StatusMessageDao statusMessageDao=DaoFactory.getFactory().getStatusMessageDao();
        UserDao userDao = DaoFactory.getFactory().getUserDao(statusMessageDao);
        if (userDao.getByUsername(username) != null) {
            errors.put("username", FormErrors.username_exists);
        }
        return new FormValidation(errors);
    }

    /**
     * Change string password to hash of this string
     *
     * @param hasher, object of {@link HashPass}
     */
    public void hashPass(HashPass hasher) {
        password = hasher.getHash(password);
    }
}
