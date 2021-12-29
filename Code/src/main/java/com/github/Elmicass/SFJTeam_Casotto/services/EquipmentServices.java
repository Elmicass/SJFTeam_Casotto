package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.repository.IEquipmentsRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class EquipmentServices implements IEquipmentServices {

    @Autowired
    private IEquipmentsRepository repository;

    @Override
    public Equipment getInstance(String id) throws EntityNotFoundException {
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
    
    
}
