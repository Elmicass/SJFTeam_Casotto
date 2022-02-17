package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.services.IReservationServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReservationsManager implements IReservationManager {

    @Autowired
    private IReservationServices services;

    @Override
    public Reservation getInstance(Integer id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public List<Reservation> getAll() {
        return services.getAll();
    }

    @Override
    public List<Reservation> getByType(BookableEntityType type) {
        return services.getByType(type);
    }

    @Override
    public boolean booking(String entityType, User user, Integer entityID,
            LocalDateTime start, LocalDateTime end) throws EntityNotFoundException, AlreadyExistingException, IllegalStateException {
        return services.booking(entityType, user, start, end, entityID);
    }

    @Override
    public boolean cancelBooking(Integer reservationID) {
        return services.cancelBooking(reservationID);
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
