package com.epam.study.snet.model;

import com.epam.study.snet.enums.Gender;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@Builder
public class User {
    @NonFinal
    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    Gender gender;

    public void setId(Long id) {
        if (this.id == null)
            this.id = id;
    }
}
