package com.epam.study.snet.entity;

import com.epam.study.snet.dao.DaoException;
import com.epam.study.snet.dao.DaoFactory;
import com.epam.study.snet.enums.Relation;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;

@Value
public class Country {
    String code;

    @SneakyThrows
//TODO SneakyThrows!!! do it normnal
    Relation checkRelation(Country otherCountry) {
        return DaoFactory.getFactory().getRelationshipDao().getRelation(this, otherCountry);
    }
}
