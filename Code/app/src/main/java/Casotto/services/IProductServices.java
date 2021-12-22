package Casotto.services;

import Casotto.model.*;

/**
 * TODO interfaccia javadoc
 */
public interface IProductServices {

    /**
     * Crea un nuovo prodotto
     * 
     * @param name        - nome del prodotto
     * @param description - descrizione del prodotto
     * @param unitPrice   - prezzo unitario del prodotto
     * @param quantity    - quantità al momento della creazione
     * @return
     */
    boolean createProduct(String name, String description, Double unitPrice, int quantity);

    /**
     * Restituisce l'oggetto Prodotto associato al dato ID
     * 
     * @param productID - ID associato a un dato prodotto
     * @return - Product
     */
    Product getProductInstance(String productID);

    /**
     * Restituisce la quantità di un dato prodotto
     * 
     * @param productID - ID associato a un dato prodotto
     * @return - quantità del dato prodotto
     */
    int getProductQuantity(String productID);

    /**
     * Restituisce la somma di delle quantità di tutti i prodotti
     * 
     * @return quantità di tutti i prodotti
     */
    int getProductsQuantity();

    /**
     * Restituisce il prezzo del singolo prodotto
     * 
     * @param productID - ID associato a un dato prodotto
     * @return - prezzo del singolo prodotto
     */
    double getProductUnitPrice(String productID);

    /**
     * Restituisce la somma di tutti i prodotti
     * 
     * @param productID - ID associato a un dato prodotto
     * @return - prezzo di tutti i prodotti
     */
    double getProductsPrice(String productID);

    /**
     * Resistuisce gli attributi del prodotto
     * 
     * @param productID - ID associato a un dato prodotto
     * @return - dati del prodotto
     */
    String[] getProductInformation(String productID);

    /**
     * TODO
     * 
     * @return
     */
    String[] getProductsInformation();

    /**
     * Aggiunge una certa quantità a un dato prodotto
     * 
     * @param productID - ID associato a un dato prodotto
     * @param quantity  - quantità da aumentare a un dato prodotto
     * @return - true se operazione riuscita, false altrimenti
     */
    boolean addProduct(String productID, int quantity);

    /**
     * Sottrae una certa quantità a un dato prodotto
     * 
     * @param productID - ID associato a un dato prodotto
     * @param quantity  - quantità da rimuovere a un dato prodotto
     * @return - true se operazione riuscita, false altrimenti
     */
    boolean subtractProduct(String productID, int quantity);

    /**
     * Rimuove un dato prodotto
     * 
     * @param productID - ID associato a un dato prodotto
     * @return - true se eliminazione è riuscita, false altrimenti
     */
    boolean delete(String productID);

}
