package com.epam.study.snet.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Date;

@Value
@Builder
public class LastMessage {
    User loggedUser;
    User companion;
    String body;
    boolean response;
    boolean haveUnread;
    Date lastMessageTime;
}
