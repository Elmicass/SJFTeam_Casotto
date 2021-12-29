package com.github.Elmicass.SFJTeam_Casotto.controller;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.services.ISunbedServices;

import org.springframework.beans.factory.annotation.Autowired;

public class SunbedsManager implements ISunbedManager {

    @Autowired
    private ISunbedServices services;

    @Override
    public Sunbed getInstance(String id) throws EntityNotFoundException {
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
    public boolean createNewSunbed() {
        // TODO Auto-generated method stub
        return false;
    }

    
    
}
