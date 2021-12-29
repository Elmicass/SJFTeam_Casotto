package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.repository.IActivitiesRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ActivityServices implements IActivityServices {
    
    @Autowired
    private IActivitiesRepository repository;

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
    public boolean createActivity(String name, String description, int maxEntries) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean booking(String activityID, String email) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean cancelBooking(String activityID, String email) {
        // TODO Auto-generated method stub
        return false;
    }

    
}
