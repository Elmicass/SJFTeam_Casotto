package com.github.Elmicass.SFJTeam_Casotto.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.services.IJobOfferServices;

public class JobOffersManager implements IJobOfferManager {

    @Autowired
    private IJobOfferServices services;

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
    public boolean createNewJobOffer(String name, String description, LocalDateTime expiration) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
