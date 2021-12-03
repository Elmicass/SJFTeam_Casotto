package Casotto.model;

/**
 * Rappresenta il ruolo base di un utente registrato all'interno dell'applicazione. Può prenotarsi per delle attività o prenotare un posto spiaggia.
 */
public interface IGuest {

/**
 * @return l'ID dell'ospite, assegnatogli al momento della registrazione.
 */
    int getID();

/** 
 * @return il nome dell'ospite.
*/
    String getName();

/** 
* @return il cognome dell'ospite.
*/
    String getSurname();

/**
 * @return l'email con cui l'ospite si è registrato.
 */
    String getEmail();

/**
 * @return // TODO
*/
    ActorRole getRole();

/**
 * 
 * @param activityID
 * @return
 */
    boolean bookActivity(String activityID);
}
