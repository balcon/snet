package com.epam.study.snet.validators;

import com.epam.study.snet.entity.User;
import com.epam.study.snet.enums.FormErrors;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public class MessageValidator {
    String body;
    User sender;
    User receiver;

    public Map<String, FormErrors> validate() {
        Map<String, FormErrors> errors = new HashMap<>();
        if (body == null || body.isEmpty()) errors.put("body", FormErrors.field_empty);
        else if (body.length() > 150) errors.put("body", FormErrors.longer_than_150);
        return errors;
    }
}
