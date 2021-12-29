package com.github.Elmicass.SFJTeam_Casotto.services;

import java.sql.Date;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.repository.IJobOffersRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class JobOfferServices implements IJobOfferServices {

    @Autowired
    private IJobOffersRepository repository;

    @Override
    public JobOffer getInstance(String id) throws EntityNotFoundException {
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
    public boolean createJobOffer(String name, String description, Date expiration) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
