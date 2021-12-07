package Casotto.model;

/**
 * Rappresenta il magazzino dello chalet, o anche l'insieme dei prodotti correntemente a disposione dello chalet.
 */
public interface IStock {

    /**
     * Restituisce la quantità presente in magazzino di un dato prodotto.
     * @param product - prodotto
     * @return - numero intero rappresentante la quantità
     */
    int getQuantity(Product product);

    /**
     * Aggiunge un dato prodotto in magazzino.
     * @param product - prodotto
     * @return - true se il prodotto è stato aggiunto correttamente, false altrimenti
     */
    boolean addProduct(Product product);

    /**
     * Rimuove un dato prodotto dal magazzino.
     * @param product - prodotto
     * @return - true se il prodotto è stato rimosso correttamente, false altrimenti
     */
    boolean removeProduct(Product product);

    /**
     * Restituisce il numero totale di prodotti presenti in magazzino.
     * @return - numero intero rappresentate il totale dei prodotti presenti
     */
    int getProductsNumber();


    
    
}
