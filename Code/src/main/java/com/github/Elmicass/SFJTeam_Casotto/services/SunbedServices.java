package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunbedsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class SunbedServices implements ISunbedServices {

    @Autowired
    private ISunbedsRepository sunbedsRepository;

    @Override
    public Sunbed getInstance(Integer id) throws EntityNotFoundException {
        return sunbedsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No sunbeds found with the given id: " + id));
    }

    @Override
    public List<Sunbed> getAll() {
        return sunbedsRepository.findAll();
    }

    @Override
    public Sunbed save(@NonNull Sunbed sunbed) {
        return sunbedsRepository.save(sunbed);
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank()) throw new IllegalArgumentException("The sunbed ID is empty");
        if (!(exists(id))) throw new EntityNotFoundException("The sunbed with ID: " + id + " does not exist");
        sunbedsRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank()) throw new IllegalArgumentException("The sunbed ID value is empty");
        return sunbedsRepository.existsById(id);
    }
    
    
}
