package Casotto.controller;

import Casotto.model.*;

/**
 * Questa interfaccia ha la responsabilità di gestire le attività
 * dell'applicazione. Sa restituire un'istanza di qualsiasi attività mediante il
 * suo ID,
 * può crearne di nuove o eliminare attività esistenti.
 */
public interface IActivityManager {

    /**
     * Restituisce un'istanza all'attività associata all'ID dato.
     * 
     * @param activityID - l'ID dell'attività cercata
     * @return un'istanza dell'attività
     */
    Activity getActivityInstance(String activityID);

    /**
     * Crea una nuova attività
     * 
     * @param name        - nome della nuova attività
     * @param description - descrizione della nuova attività
     * @param maxEntries  - numero massimo di utenti per l'attività
     * @return - true se la creazione è completata correttamente, false altrimenti
     */
    boolean createNewActivity(String name, String description, int maxEntries);

    /**
     * Aggiunge la prenotazione di un utente ad una determinata attività.
     * 
     * @param activityID - l'ID dell'attività selezionata
     * @param email      - email dell'utente che intende prenotarsi
     * @return - true se l'utente si è prenotato con successo, false altrimenti
     */
    boolean newBooking(String activityID, String email);

    /**
     * Rimuove la prenotazione di un utente da una determinata attività.
     * 
     * @param activityID - l'ID dell'attività selezionata
     * @param email      - email dell'utente che intende cancellare la sua
     *                   prenotazione
     * @return - true se la prenotazione è stata cancellata con successo, false
     *         altrimenti
     */
    boolean cancelBooking(String activityID, String email);

    /**
     * Elimina l'attività associata al ID passato
     * 
     * @param activityID - ID associato all attività selezionata
     * @return - true se la cancellazione è avvenuta, false altrimenti
     */
    boolean deleteActivity(String activityID);

}
