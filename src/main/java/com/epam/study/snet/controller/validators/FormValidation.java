package com.epam.study.snet.controller.validators;

import com.epam.study.snet.view.Enums.FormErrors;

import java.util.HashMap;
import java.util.Map;

public class FormValidation {
    private Map<String, FormErrors> errors;

    public FormValidation() {
        errors = new HashMap<>();
    }

    public FormValidation(Map<String, FormErrors> errors) {
        this.errors = errors;
    }

    public Map<String, FormErrors> getErrors() {
        return errors;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }


}
