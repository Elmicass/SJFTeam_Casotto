package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunshadesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class SunshadeServices implements ISunshadeServices {

    @Autowired
    private ISunshadesRepository sunshadeRepository;

    @Override
    public Sunshade getInstance(String id) throws EntityNotFoundException {
        return sunshadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No sunshades found with the given id: " + id));
    }

    @Override
    public boolean saveSunshade(@NonNull Sunshade sunshade) {
        sunshadeRepository.save(sunshade);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank()) throw new IllegalArgumentException("The sunshade ID is empty");
        if (!(exists(id))) throw new EntityNotFoundException("The sunshade with ID: " + id + " does not exist");
        sunshadeRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank()) throw new IllegalArgumentException("The sunshade ID value is empty");
        return sunshadeRepository.existsById(id);
    }
    
    
}
