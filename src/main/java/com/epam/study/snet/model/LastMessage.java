package com.epam.study.snet.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class LastMessage {
    User loggedUser;
    User companion;
    String body;
    boolean response;
    boolean haveUnread;
    LocalDateTime lastMessageTime;
    int numberUnread;
}
