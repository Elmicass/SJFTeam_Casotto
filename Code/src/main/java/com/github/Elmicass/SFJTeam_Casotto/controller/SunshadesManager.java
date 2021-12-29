package com.github.Elmicass.SFJTeam_Casotto.controller;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;
import com.github.Elmicass.SFJTeam_Casotto.services.ISunshadeServices;

import org.springframework.beans.factory.annotation.Autowired;

public class SunshadesManager implements ISunshadeManager {

    @Autowired
    private ISunshadeServices services;

    @Override
    public Sunshade getInstance(String id) throws EntityNotFoundException {
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
    public boolean createNewSunshade(SunshadeType type) {
        // TODO Auto-generated method stub
        return false;
    }

    
    
}
