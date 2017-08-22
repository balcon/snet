package com.epam.study.snet.controller.services;

import com.epam.study.snet.model.entity.Country;
import com.epam.study.snet.model.enums.Relation;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static com.epam.study.snet.model.enums.Relation.GOOD;

@Value
public class RelationManager {

    private List<Relationship> relations;

    public Relation checkRelation(Country country1, Country country2) {
        Relation relation = Relation.NEUTRAL;
        if (country1.equals(country2)) return Relation.SAME;
        for (Relationship relationship : relations) {
            if ((relationship.getCountry1().equals(country1)
                    && relationship.getCountry2().equals(country2))
                    || (relationship.getCountry1().equals(country2)
                    && relationship.getCountry2().equals(country1))) {
                return relationship.getRelation();
            }
        }
        return relation;
    }

    public List<Country> getBadRelations(Country country) {
        return getCountriesList(country,Relation.BAD);
    }

    public List<Country> getGoodRelations(Country country) {
        return getCountriesList(country,GOOD);
    }

    private List<Country> getCountriesList(Country country,Relation relation) {
        List<Country> countries = new ArrayList<>();
        for (Relationship relationship : relations) {

            if (relationship.getCountry1().equals(country)
                    && relationship.getRelation() == relation) countries.add(relationship.getCountry2());
            if (relationship.getCountry2().equals(country)
                    && relationship.getRelation() == relation) countries.add(relationship.getCountry1());

        }
        return countries;
    }

    @Value
    public static class Relationship {
        Country country1;
        Country country2;
        Relation relation;
    }
}
