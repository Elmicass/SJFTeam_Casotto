package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunbedsRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class SunbedServices implements ISunbedServices {

    @Autowired
    private ISunbedsRepository repository;

    @Override
    public Sunbed getInstance(String id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No sunbeds found with the given id: " + id));
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
    public boolean createSunbed() {
        // TODO Auto-generated method stub
        return false;
    }

    
    
}
