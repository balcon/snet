package com.epam.study.snet.entity;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;

@Value
@Builder
public class Message {
    @NonFinal
    Long id;
    User sender;
    User receiver;
    String body;
    boolean unread;
    @NonFinal
    LocalDateTime sendingTime;

    public void setId(Long id) {
        if (this.id == null)
            this.id = id;
    }

    public void setSendingTime(LocalDateTime sendingTime){
        if(this.sendingTime==null)
            this.sendingTime=sendingTime;
    }

}
