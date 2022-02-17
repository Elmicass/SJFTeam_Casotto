package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;

public interface IActivityManager extends EntityManager<Activity> {

    boolean createNewActivity(String name, String description, int maxEntries, LocalDateTime startTime,
            LocalDateTime endTime, String[] equipments) throws AlreadyExistingException;
    
}
