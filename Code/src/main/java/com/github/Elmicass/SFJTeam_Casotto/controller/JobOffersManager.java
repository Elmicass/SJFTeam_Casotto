package com.github.Elmicass.SFJTeam_Casotto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.services.IJobOfferServices;

@Controller
public class JobOffersManager implements IJobOfferManager {

    @Autowired
    private IJobOfferServices services;

    @Override
    public JobOffer getInstance(String id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public List<JobOffer> getAll() {
        return services.getAll();
    }

    @Override
    public boolean createJobOffer(String name, String description, LocalDateTime start, LocalDateTime expiration)
            throws AlreadyExistingException {
        return services.createJobOffer(name, description, start, expiration);
    }
    
    @Override
    public boolean delete(String id) {
        return services.delete(id);
    }

    @Override
    public boolean exists(String id) {
        return services.exists(id);
    }

    
    
}
