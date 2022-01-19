package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;

/**
 * Questa interfaccia è responsabile della gestione di tutte le offerte di lavoro nel sistema.
 * Sa restituire un'istanza di qualsiasi offerta mediante il suo ID, può crearne di nuove o eliminarne di esistenti.
 */
public interface IJobOfferServices extends EntityServices<JobOffer, String>{

    /**
     * 
     * @param name
     * @param description
     * @param start
     * @param expiration
     * @return
     * @throws AlreadyExistingException
     */
    boolean createJobOffer(String name, String description, LocalDateTime start, LocalDateTime expiration) throws AlreadyExistingException;
 
    /**
     * 
     * @param jobOfferID
     * @param reservation
     * @return
     */
    boolean application(String jobOfferID, Reservation reservation);

}
