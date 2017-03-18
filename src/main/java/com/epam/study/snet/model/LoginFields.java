package com.epam.study.snet.model;

import com.epam.study.snet.enums.FormErrors;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
@Builder
public class LoginFields {
    String username;
    String password;

    public Map<String,FormErrors> validate(){
        Map<String,FormErrors> errors=new HashMap<>();
        if(username==null||username.isEmpty()) errors.put("username",FormErrors.field_empty);
        if(password==null||password.isEmpty()) errors.put("password",FormErrors.field_empty);
        return errors;
    }
}
