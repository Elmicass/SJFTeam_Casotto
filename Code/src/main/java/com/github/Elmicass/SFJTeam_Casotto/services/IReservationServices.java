package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation.EntityType;

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
    Reservation createReservation(EntityType type, String user, String entityID, LocalDateTime start, LocalDateTime end, Object object) throws AlreadyExistingException;
    
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
    boolean booking(String type, String user, String entityID, LocalDateTime start, LocalDateTime end) throws AlreadyExistingException;



}
