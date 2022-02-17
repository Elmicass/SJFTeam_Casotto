package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.EnumSet;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment.EquipmentType;
import com.github.Elmicass.SFJTeam_Casotto.repository.IEquipmentsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class EquipmentServices implements IEquipmentServices {

    @Autowired
    private IEquipmentsRepository eqRepository;

    @Override
    public Equipment getInstance(Integer id) throws EntityNotFoundException {
        return eqRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No equipment found with the given id: " + id));
    }

    @Override
    public List<Equipment> getAll() {
        return eqRepository.findAll();
    }

    @Override
    public boolean createEquipment(@NonNull String name, @NonNull String description, @NonNull String type) {
        if (equipmentCreationErrorsCheckin(type)) {
            Equipment equipment =  new Equipment(name, description, type);
            eqRepository.save(equipment);
            return true;
        } else return false;
    }
    
    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The equipment ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The equipment with ID: " + id + " does not exist");
        eqRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The equipment ID value is empty");
        return eqRepository.existsById(id);
    }

    public boolean equipmentCreationErrorsCheckin(String type) {
        if (!(EnumSet.allOf(EquipmentType.class).contains(EquipmentType.valueOf(type))))
            throw new IllegalArgumentException("The equipment type you entered is not valid. Valid values are: "+EquipmentType.values()+".");
        return true;
    }

    
    
}
