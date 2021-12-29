package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Activity;

/**
 * Questa interfaccia è responsabile della gestione di tutte le attività nel sistema.
 * Sa restituire un'istanza di qualsiasi attività mediante il suo ID, può crearne di nuove o eliminarne di esistenti.
 */
public interface IActivityServices extends EntityServices<Activity, String>{

    /**
     * 
     * @param name
     * @param description
     * @param maxEntries
     * @return
     */
    boolean createActivity(String name, String description, int maxEntries);

    /**
     * Aggiunge la prenotazione di un utente ad una determinata attività.
     * @param activityID - l'ID dell'attività selezionata
     * @param email - email dell'utente che intende prenotarsi
     * @return - true se l'utente si è prenotato con successo, false altrimenti
     */
    boolean booking(String activityID, String email);

    /**
     * Rimuove la prenotazione di un utente da una determinata attività.
     * @param activityID - l'ID dell'attività selezionata
     * @param email - email dell'utente che intende cancellare la sua prenotazione
     * @return - true se la prenotazione è stata cancellata con successo, false altrimenti
     */
    boolean cancelBooking(String activityID, String email);

}