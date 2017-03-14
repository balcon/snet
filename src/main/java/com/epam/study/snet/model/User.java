package com.epam.study.snet.model;

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

    public void setId(Long id) {
        if (this.id == null)
            this.id = id;
    }
}
