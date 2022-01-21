package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunbedsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class SunbedServices implements ISunbedServices {

    @Autowired
    private ISunbedsRepository sunbedsRepository;

    @Override
    public Sunbed getInstance(String id) throws EntityNotFoundException {
        return sunbedsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No sunbeds found with the given id: " + id));
    }

    @Override
    public boolean saveSunbed(@NonNull Sunbed sunbed) {
        sunbedsRepository.save(sunbed);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank()) throw new IllegalArgumentException("The sunbed ID is empty");
        if (!(exists(id))) throw new EntityNotFoundException("The sunbed with ID: " + id + " does not exist");
        sunbedsRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank()) throw new IllegalArgumentException("The sunbed ID value is empty");
        return sunbedsRepository.existsById(id);
    }
    
    
}
