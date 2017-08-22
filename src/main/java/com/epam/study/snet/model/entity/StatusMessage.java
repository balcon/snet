package com.epam.study.snet.model.entity;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class StatusMessage {
    long id;
    String body;
    LocalDateTime sendingTime;
}
