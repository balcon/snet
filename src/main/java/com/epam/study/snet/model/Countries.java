package com.epam.study.snet.model;

import java.util.*;

public class Countries {
    private static Map<String, String> countries;

    static {
        countries = new HashMap<>();
        String[] isoCountries = Locale.getISOCountries();
        for (String isoCountry : isoCountries) {
            String displayCountry = new Locale("", isoCountry).getDisplayCountry(Locale.ENGLISH);
            countries.put(isoCountry, displayCountry);
        }
    }

    public static Map<String, String> getCountries() {
        return countries;
    }
}
