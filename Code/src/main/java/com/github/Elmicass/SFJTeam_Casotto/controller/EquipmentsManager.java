package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.services.IEquipmentServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EquipmentsManager implements IEquipmentManager {

    @Autowired
    private IEquipmentServices services;

    @Override
    public Equipment getInstance(Integer id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public List<Equipment> getAll() {
        return services.getAll();
    }

    @Override
    public Equipment save(Equipment object) {
        return services.save(object);
    }
    
    @Override
    public boolean createEquipment(String name, String description, String type) {
        return services.createEquipment(name, description, type);
    }
    
    @Override
    public boolean delete(Integer id) {
        return services.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return services.exists(id);
    }

    

    
    
}
