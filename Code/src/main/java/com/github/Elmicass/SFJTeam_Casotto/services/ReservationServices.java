package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.repository.IReservationsRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ReservationServices implements IReservationServices {

    @Autowired
    private IReservationsRepository repository;

    @Override
    public Reservation getInstance(String id) throws EntityNotFoundException {
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
