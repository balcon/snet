package com.epam.study.snet.validators;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.enums.FormErrors;
import com.epam.study.snet.model.FormValidation;
import com.epam.study.snet.model.HashPass;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code LoginValidator} class is a 'layer' between users input
 * and applications business logic. This class validates input data
 * and checks username and password {@see validate}.
 *
 * @author Konstantin Balykov
 * @since 0.9b
 */
@Value
@Builder
public class LoginValidator {
    String username;
    String password;

    /**
     * Method validate input fields and check username and password.
     * @return Object of {@link FormValidation} class.
     * @throws DaoException
     */
    public FormValidation validate() throws DaoException {
        Map<String, FormErrors> errors = new HashMap<>();
        if (username == null || username.isEmpty()) errors.put("username", FormErrors.field_empty);
        if (password == null || password.isEmpty()) errors.put("password", FormErrors.field_empty);
        User user = DaoFactory.getFactory().getUserDao().getByUsername(username);
        String passHash = new HashPass().getHash(password);
        if (errors.isEmpty() && (user == null || !user.getPassword().equals(passHash)))
            errors.put("loginForm", FormErrors.bad_login_password);
        return new FormValidation(errors);
    }
}
