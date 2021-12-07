package Casotto.controller;

import Casotto.model.*;

/**
 * Quest'interfaccia ha la responsabilità di gestire il magazzino (quindi i prodotti contenuti in esso) dello chalet. Sa restituire l'unica instanza del magazzino esistente,
 * può aggiungere o rimuovere prodotti da quest'ultimo.
 */
public interface IStockManager {

    /**
     * Restitusce l'istanza del magazzino esistente.
     * @return - l'istanza del magazzino
     */
    Stock getStockInstance();

    /**
     * Restituisce la quantità del prodotto, associato all'ID dato, presente in magazzino.
     * @param productID
     * @return
     */
    int getProductQuantity(String productID);

    /**
     * Aggiunge la quantità data di prodotto (già esistente) dato in magazzino.
     * @param productID - l'ID del prodotto
     * @param quantity - la quantità che si vuole aggiungere
     * @return - true se la quantità di prodotto è stata aggiunta correttamente, false altrimenti
     */
    boolean addProduct(String productID, int quantity);
    
    /**
     * Sottrae la quantità data di prodotto dato dal magazzino. 
     * @param productID - l'ID del prodotto
     * @param quantity - la quantità che si vuole sottrarre
     * @return - true se la quantità di prodotto è stata rimossa correttamente, false altrimenti
     */
    boolean subtractProduct(String productID, int quantity);



}
