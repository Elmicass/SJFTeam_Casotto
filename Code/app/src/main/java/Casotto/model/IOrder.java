package Casotto.model;

/**
 * Rappresenta una singola ordinazione al servizio bar da parte di un cliente.
 */
public interface IOrder {

    /**
     * Restituisce l'ID dell'ordinazione.
     * @return - ID
     */
    String getID();

    /**
     * Restituisce il cliente associato all'ordinazione.
     * @return - cliente
     */
    Customer getClient();

    /**
     * Restituisce la lista di prodotti associati all'ordinazione.
     * @return - lista di prodotti
     */
    Product[] getProducts();

    /**
     * Restituisce il saldo totale associato all'ordinazione.
     * @return - totale ordinazione
     */
    double getDueAmount();

    /**
     * Aggiunge il prodotto selezionato alla lista dei prodotti associati all'ordinazione.
     * @return - true se il prodotto è stato aggiunto correttamente, false altrimenti
     */
    boolean addProduct(Product product);

     /**
     * Rimuove il prodotto selezionato dalla lista dei prodotti associati all'ordinazione.
     * @return - true se il prodotto è stato rimosso correttamente, false altrimenti
     */
    boolean rimuoviProdotto(Product product);

}