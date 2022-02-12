package com.github.Elmicass.SFJTeam_Casotto.model;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;

public interface IEntity {

    public enum BookableEntityType {
        BeachPlace,
        Activity,
        JobOffer;
    }

    /**
     * 
     * @param reservation
     * @return
     * @throws IllegalStateException
     * @throws AlreadyExistingException
     */
    public boolean addReservation(Reservation reservation) throws AlreadyExistingException, IllegalStateException;

    /**
     * 
     * @param reservation
     * @return
     */
    public boolean removeReservation(Reservation reservation);
    
}
