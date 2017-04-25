package com.epam.study.snet.entity;

import com.epam.study.snet.enums.Gender;
import com.epam.study.snet.model.Image;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDate;

@Value
@Builder
public class User {
    @NonFinal
    Long id;
    String username;
    @NonFinal
    String password;
    String firstName;
    String lastName;
    LocalDate birthday;
    Gender gender;
    Image photo;
    boolean deleted;

    public void setId(Long id) {
        if (this.id == null)
            this.id = id;
    }
    public void setPassword(String password){
        this.password=password;
    }
}
