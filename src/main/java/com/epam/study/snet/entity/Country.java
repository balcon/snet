package com.epam.study.snet.entity;

import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.enums.Relation;
import lombok.SneakyThrows;
import lombok.Value;

import java.util.List;

@Value
public class Country {
    String code;

    @SneakyThrows
//TODO SneakyThrows!!! do it normnal
    Relation checkRelation(Country otherCountry) {
        return DaoFactory.getFactory().getRelationshipDao().getRelation(this, otherCountry);
    }

    @SneakyThrows
//TODO SneakyThrows!!! do it normnal
    public List<Country> getBadRelations() {
        return DaoFactory.getFactory().getRelationshipDao().getListRelations(this, Relation.BAD);
    }

    @SneakyThrows
//TODO SneakyThrows!!! do it normnal
    public List<Country> getGoodRelations() {
        return DaoFactory.getFactory().getRelationshipDao().getListRelations(this, Relation.GOOD);

    }
}
