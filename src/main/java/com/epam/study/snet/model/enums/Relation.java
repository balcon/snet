package com.epam.study.snet.model.enums;

public enum Relation {
    BAD,
    GOOD,
    NEUTRAL,
    SAME;

    public static Relation parse(String string){
        if(string.equalsIgnoreCase("BAD")) return BAD;
        if(string.equalsIgnoreCase("GOOD")) return GOOD;
        if(string.equalsIgnoreCase("SAME")) return SAME;
        return NEUTRAL;
    }
}
