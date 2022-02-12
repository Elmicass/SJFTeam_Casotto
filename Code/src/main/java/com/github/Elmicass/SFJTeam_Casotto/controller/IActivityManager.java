package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.time.LocalDateTime;
import java.util.Set;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;

public interface IActivityManager extends EntityManager<Activity, String> {

    boolean createNewActivity(String name, String description, int maxEntries, LocalDateTime startTime,
            LocalDateTime endTime, Set<String> equipments) throws AlreadyExistingException;
    
}
