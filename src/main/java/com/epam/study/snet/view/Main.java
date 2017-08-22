package com.epam.study.snet.view;

import com.epam.study.snet.model.entity.StatusMessage;
import com.epam.study.snet.model.entity.User;
import com.epam.study.snet.controller.services.Countries;
import com.epam.study.snet.controller.services.RelationManager;
import lombok.Data;

import java.util.List;

@Data
public class Main {
    User user;
    Countries countries;
    RelationManager relationManager;
    List<User> compatriots;
    StatusMessage statusMessage;
    boolean itself=false;
}
