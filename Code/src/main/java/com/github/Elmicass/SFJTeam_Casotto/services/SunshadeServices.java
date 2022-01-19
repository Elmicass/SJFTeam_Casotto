package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunshadesRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class SunshadeServices implements ISunshadeServices {

    @Autowired
    private ISunshadesRepository repository;

    @Override
    public Sunshade getInstance(String id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No sunshades found with the given id: " + id));
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
    public boolean createSunshade(SunshadeType type) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
