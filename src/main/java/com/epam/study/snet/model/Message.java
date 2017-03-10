package com.epam.study.snet.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Message {
    long id;
    User sender;
    User receiver;
    String body;
    LocalDateTime sendingTime;
}
