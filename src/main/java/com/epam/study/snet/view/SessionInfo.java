package com.epam.study.snet.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SessionInfo {
    @Getter
    private long unreadMessages;
    @Getter
    private long registeredUsers;
}
