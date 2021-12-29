package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.repository.IBeachPlacesRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class BeachPlaceServices implements IBeachPlaceServices {

    @Autowired
    private IBeachPlacesRepository repository;

    @Override
    public BeachPlace getInstance(String id) throws EntityNotFoundException {
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
    public boolean createBeachPlace(int seaRowNumber, Sunshade sunshade, Sunbed[] sunbeds) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
