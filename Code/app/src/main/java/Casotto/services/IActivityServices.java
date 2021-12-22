package Casotto.services;

import Casotto.model.*;

public interface IActivityServices {

    /**
     * Crea una attività
     * 
     * @param name        - nome della nuova attività
     * @param description - descrizione della nuova attività
     * @param maxEntries  - numero massimo di utenti per l'attività
     * @return
     */
    boolean createActivity(String name, String description, int maxEntries);

    /**
     * Restituisce l'oggetto con il determinato ID che si passa
     * 
     * @param activityID - ID associata all'attività
     * @return - oggetto Activity
     */
    Activity getActivityInstance(String activityID);

    /**
     * Aggiunge la prenotazione di un utente ad una determinata attività
     * 
     * @param activityID - l'ID dell'attività selezionata
     * @param guest      - ospide da aggiungere
     * @return - true se la prenotazione è avvenuta, false altrimenti
     */
    boolean booking(String activityID, Guest guest);

    /**
     * Rimuove l'attività tramite l'ID
     * 
     * @param activityID - l'ID dell'attività selezionata
     * @return - true se l'eliminazione è avvenuta, false altrimenti
     */
    boolean delete(String activityID);
}
