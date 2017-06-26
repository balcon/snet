package com.epam.study.snet.entity;

import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.enums.Relation;
import com.epam.study.snet.model.Image;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Value
@Builder
public class User {
    long id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate birthday;
    Gender gender;
    Image photo;
    Country country;
    boolean deleted;

    public Relation checkRelation(User otherUser) {
        return country.checkRelation(otherUser.getCountry());
    }

    public long getAge(){
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}
