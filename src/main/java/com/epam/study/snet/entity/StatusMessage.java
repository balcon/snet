package com.epam.study.snet.entity;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;

@Value
@Builder
public class StatusMessage {
    long id;
    String body;
    LocalDateTime sendingTime;
}
