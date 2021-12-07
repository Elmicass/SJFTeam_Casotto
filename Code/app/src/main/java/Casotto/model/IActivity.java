package Casotto.model;

/**
 * Rappresenta una singola attività organizzata nello chalet.
 */
public interface IActivity {

    /**
     * Restituisce il numero massimo di partecipanti previsti per quell'attività. 
     * @return - un numero intero rappresentante il limite di prenotazioni consentite
     */
    Integer getMaxEntries();

    /**
     * Restituisce il numero corrente di partecipanti già prenotati.
     * @return - un numero intero che rappresenta ...
     */
    Integer getParticipantsNumber();

    /**
     * Aggiunge una prenotazione a carico dell'utente dato, alla lista dei partecipanti all'attività. 
     * @param user - l'utente che intende prenotarsi
     * @return - true se la prenotazione è stata aggiunta correttamente, false altrimenti
     */
    boolean addReservation(Guest user);

   /**
     * Rimuove la prenotazione a carico dell'utente dato, dalla lista dei partecipanti all'attività. 
     * @param user - l'utente che intende cancellare la propria prenotazione
     * @return - true se la prenotazione è stata rimossa correttamente, false altrimenti
     */
    boolean removeReservation(Guest user);
    
}
