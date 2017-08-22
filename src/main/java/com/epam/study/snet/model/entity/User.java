package com.epam.study.snet.model.entity;

import com.epam.study.snet.model.enums.Gender;
import com.epam.study.snet.view.Image;
import lombok.Builder;
import lombok.Value;

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
    StatusMessage statusMessage;

    public long getAge(){
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}
