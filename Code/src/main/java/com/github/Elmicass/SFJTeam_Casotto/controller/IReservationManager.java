package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.User;

public interface IReservationManager extends EntityManager<Reservation, String> {

    boolean booking(String entityType, User user, String entity,
            LocalDateTime start, LocalDateTime end) throws EntityNotFoundException, AlreadyExistingException;

    boolean cancelBooking(String reservationID);

}
