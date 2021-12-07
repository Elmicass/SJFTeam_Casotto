package Casotto.model;

/**
 * Rappresenta il ruolo base di un utente registrato all'interno dell'applicazione. Può prenotarsi per delle attività o prenotare un posto spiaggia.
 */
public interface IGuest {

/**
 * Restituisce l'ID dell'ospite, assegnatogli al momento della registrazione.
 * @return - ID
 */
    int getID();

/** 
 * Restituisce il nome dell'ospite.
 * @return - nome
*/
    String getName();

/** 
 * Restituisce il cognome dell'ospite. 
 * @return cognome
*/
    String getSurname();

/**
 * Restituisce l'email con cui l'ospite si è registrato.
 * @return - email
 */
    String getEmail();

/**
 * 
 * @return // TODO
*/
    ActorRole getRole();

/**
 * L'ospite si prenota per l'attività selezionata.
 * @param activity - attività
 * @return - true se la prenotazione è avvenuta con successo, false altrimenti
 */
    boolean bookActivity(Activity activity);


    
}
