package com.github.Elmicass.SFJTeam_Casotto.services;

import java.sql.Date;

import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;

/**
 * Questa interfaccia è responsabile della gestione di tutte le offerte di lavoro nel sistema.
 * Sa restituire un'istanza di qualsiasi offerta mediante il suo ID, può crearne di nuove o eliminarne di esistenti.
 */
public interface IJobOfferServices extends EntityServices<JobOffer, String>{

    /**
     * 
     * @param name
     * @param description
     * @param expiration
     * @return
     */
    boolean createJobOffer(String name, String description, Date expiration);
        
}
