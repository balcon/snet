package com.epam.study.snet.model;

import java.util.*;

public class Countries {
    private Locale locale;

    public Countries() {
        this(Locale.ENGLISH);
    }

    public Countries(Locale locale) {
        this.locale = locale;
    }


    public Map<String, String> getList() {
        Map<String, String> countries = new HashMap<>();
        String[] isoCountries = Locale.getISOCountries();
        for (String isoCountry : isoCountries) {
            String displayCountry = new Locale("", isoCountry).getDisplayCountry(locale);
            countries.put(isoCountry, displayCountry);
        }
        return sort(countries);
    }

    private Map<String, String> sort(Map<String, String> map) {
        List<Map.Entry<String, String>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        map = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : list) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

}
