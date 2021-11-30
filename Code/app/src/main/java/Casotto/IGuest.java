
/**
 * Questa intefaccia definisce i metodi base del ruolo "ospite"
 */
public interface IGuest {

/**
 * getID() restituisce ID del cliente
 * @return ID ospite
 */
    getID();

/** 
 * getName() restitusce il nome del ospite.
 * @return Nome Cognome del ospite
*/
    getName();

/** 
* getSurname() restitusce il nome del ospite.
* @return Nome Cognome del ospite
*/
    getSurname();

/**
 * getEmail() restituisce l'email del ospite.
 * @return email del ospite
 */
    getEmail();

/**
 * getRole() restituisce il ruolo del ospite.
 * @return ruolo del ospite
*/
    getRole();

}
