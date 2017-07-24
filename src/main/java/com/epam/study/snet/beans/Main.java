package com.epam.study.snet.beans;

import com.epam.study.snet.entity.StatusMessage;
import com.epam.study.snet.entity.User;
import com.epam.study.snet.model.Countries;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
public class Main {
    User user;
    Countries countries;
    List<User> compatriots;
    StatusMessage statusMessage;
    boolean itself=false;
}
