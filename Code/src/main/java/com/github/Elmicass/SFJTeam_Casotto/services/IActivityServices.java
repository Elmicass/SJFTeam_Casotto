package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;

/**
 * Questa interfaccia è responsabile della gestione di tutte le attività nel sistema.
 * Sa restituire un'istanza di qualsiasi attività mediante il suo ID, può crearne di nuove o eliminarne di esistenti.
 */
public interface IActivityServices extends EntityServices<Activity>{

    /**
     * 
     * @param name
     * @param description
     * @param maxEntries
     * @param startTime
     * @param endTime
     * @param equipments
     * @return
     * @throws AlreadyExistingException
     */
    boolean createActivity(String name, String description, Integer maxEntries, LocalDateTime startTime, LocalDateTime endTime, String[] equipments) throws AlreadyExistingException;

    /**
     * Aggiunge la prenotazione di un utente ad una determinata attività.
     * @param activityID - l'ID dell'attività selezionata
     * @param email - email dell'utente che intende prenotarsi
     * @return - true se l'utente si è prenotato con successo, false altrimenti
     * @throws AlreadyExistingException
     * @throws IllegalStateException
     */
    boolean booking(Integer activityID, Reservation reservation) throws IllegalStateException, AlreadyExistingException;

    /**
     * Rimuove la prenotazione di un utente da una determinata attività.
     * @param activityID - l'ID dell'attività selezionata
     * @param email - email dell'utente che intende cancellare la sua prenotazione
     * @return - true se la prenotazione è stata cancellata con successo, false altrimenti
     */
    boolean cancelBooking(Reservation reservation, Integer activityID);

}
