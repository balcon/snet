package com.epam.study.snet.model.enums;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender parse(String string){
        if(string.equalsIgnoreCase("male")) return MALE;
        return FEMALE;
    }
}
