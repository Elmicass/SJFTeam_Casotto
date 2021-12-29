package com.github.Elmicass.SFJTeam_Casotto.controller;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment.EquipmentType;
import com.github.Elmicass.SFJTeam_Casotto.services.IEquipmentServices;

import org.springframework.beans.factory.annotation.Autowired;

public class EquipmentsManager implements IEquipmentManager {

    @Autowired
    private IEquipmentServices services;

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

    @Override
    public boolean createNewEquipment(String name, String description, EquipmentType type) {
        // TODO Auto-generated method stub
        return false;
    }

    
    
}
