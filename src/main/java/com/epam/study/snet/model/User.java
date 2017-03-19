package com.epam.study.snet.model;

import com.epam.study.snet.enums.Gender;
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
    String password;
    String firstName;
    String lastName;
    LocalDate birthday;
    Gender gender;
    Image photo;

    public void setId(Long id) {
        if (this.id == null)
            this.id = id;
    }
}
