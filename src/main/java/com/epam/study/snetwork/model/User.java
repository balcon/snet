package com.epam.study.snetwork.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    long id;
    String firstName;
    String lastName;

    String username;
    String passHash;
}
