package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;

public interface IReservationManager extends EntityManager<Reservation> {

    /**
     * 
     * @param type
     * @return
     */
    List<Reservation> getByType(BookableEntityType type);

    /**
     * 
     * @param entityType
     * @param user
     * @param entityID
     * @param start
     * @param end
     * @return
     * @throws EntityNotFoundException
     * @throws AlreadyExistingException
     */
    boolean booking(String entityType, User user, Integer entityID,
            LocalDateTime start, LocalDateTime end) throws EntityNotFoundException, AlreadyExistingException;

    /**
     * 
     * @param reservationID
     * @return
     */
    boolean cancelBooking(Integer reservationID);

}
