package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.services.IActivityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ActivitiesManager implements IActivityManager {

    @Autowired
    private IActivityServices services;

    @Override
    public Activity getInstance(Integer id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public List<Activity> getAll() {
        return services.getAll();
    }

    @Override
    public Activity save(Activity object) {
        return services.save(object);
    }
    
    @Override
    public boolean createNewActivity(String name, String description, int maxEntries, LocalDateTime startTime,
            LocalDateTime endTime, String[] equipments) throws AlreadyExistingException {
        return services.createActivity(name, description, maxEntries, startTime, endTime, equipments);
    }

    @Override
    public boolean delete(Integer id) throws EntityNotFoundException, IllegalArgumentException {
        return services.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return services.exists(id);
    }
    
}
