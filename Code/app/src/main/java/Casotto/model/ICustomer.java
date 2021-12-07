package Casotto.model;

/**
 * Rappresenta il ruolo di un utente attualmente intestatario di un posto spiaggia. Puà prenotarsi a delle attività o ordinare dal servizio bar.
 */
public interface ICustomer extends IGuest {

    /**
     * Restituisce il posto spiaggia di cui il cliente è intestatario.
     * @return - posto spiaggia del cliente
     */
    BeachPlace getBeachPlace();

    /**
     * Crea una nuova ordinazione vuota a carico del cliente.
     * @return - true se l'ordinazione è stata creata con successo, false altrimenti
     */
    boolean createOrder();




}