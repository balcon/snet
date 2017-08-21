package com.epam.study.snet.entity;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.enums.Relation;
import lombok.SneakyThrows;
import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Value
public class Country {
    private String code;

//
//    Relation checkRelation(Country otherCountry) {
//        if (otherCountry==this) return Relation.SAME;
//        return relations.getOrDefault(otherCountry,Relation.NEUTRAL);
//    }
//
//    public Set<Country> getBadRelations() {
//        Set<Country> badRelations=new HashSet<>();
//        for (Map.Entry<Country, Relation> relationEntry : relations.entrySet()) {
//            if(relationEntry.getValue()==Relation.BAD) badRelations.add(relationEntry.getKey());
//        }
//        return badRelations;
//    }
//
//    public Set<Country> getGoodRelations() {
//        Set<Country> badRelations=new HashSet<>();
//        for (Map.Entry<Country, Relation> relationEntry : relations.entrySet()) {
//            if(relationEntry.getValue()==Relation.GOOD) badRelations.add(relationEntry.getKey());
//        }
//        return badRelations;
//    }

}
