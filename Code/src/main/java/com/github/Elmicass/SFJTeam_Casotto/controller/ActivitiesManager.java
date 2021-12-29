package com.github.Elmicass.SFJTeam_Casotto.controller;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.services.IActivityServices;

import org.springframework.beans.factory.annotation.Autowired;

public class ActivitiesManager implements IActivityManager {

    @Autowired
    private IActivityServices services;
    
    @Override
    public boolean newBooking(String activityID, String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean cancelBooking(String activityID, String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Activity getInstance(String id) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean exists(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean createNewActivity(String name, String description, int maxEntries) {
        // TODO Auto-generated method stub
        return false;
    }

   



    
}
