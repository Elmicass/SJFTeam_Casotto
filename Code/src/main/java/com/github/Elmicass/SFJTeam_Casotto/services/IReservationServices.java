package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.User;

/**
 * 
 * 
 */
public interface IReservationServices extends EntityServices<Reservation, String> {

    /**
     * 
     * @param type
     * @param user
     * @param entityID
     * @param start
     * @param end
     * @param object
     * @return
     * @throws AlreadyExistingException
     */
    Reservation createReservation(BookableEntityType type, User user, LocalDateTime start, LocalDateTime end, IEntity object) throws AlreadyExistingException;
    
    /**
     * 
     * @param type
     * @param user
     * @param entityID
     * @param start
     * @param end
     * @return
     * @throws AlreadyExistingException
     */
    boolean booking(String type, User user, LocalDateTime start, LocalDateTime end, String entityID) throws AlreadyExistingException, IllegalStateException, EntityNotFoundException;

    /**
     * 
     * @param reservationID
     * @return
     */
    boolean cancelBooking(String reservationID);



}
