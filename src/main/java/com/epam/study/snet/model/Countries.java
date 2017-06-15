package com.epam.study.snet.model;

import com.epam.study.snet.entity.Country;

import java.util.*;

public class Countries {
    private  Map<String, String> countries;

    public Countries(Locale locale) {
        countries = new HashMap<>();
        String[] isoCountries = Locale.getISOCountries();
        for (String isoCountry : isoCountries) {
            String displayCountry = new Locale("", isoCountry).getDisplayCountry(locale);
            countries.put(isoCountry, displayCountry);
        }
    }

    public Map<String, String> getList() {
        return sort(countries);
    }

    public String getName(Country country){
        return countries.get(country.getCode());
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
