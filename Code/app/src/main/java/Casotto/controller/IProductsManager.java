package Casotto.controller;

import Casotto.model.*;

/**
 * Quest'interfaccia ha la responsabilità di gestire i prodotti offerti dallo chalet nel servizio bar. Sa restituire un'istanza di qualsiasi prodotto mediante il suo ID,
 * può crearne di nuovi, o eliminare prodotti esistenti. 
 */
public interface IProductsManager {

    /**
     * Restituisce un'istanza del prodotto associato all'ID dato.
     * @param productID - l'ID del prodotto ricercato
     * @return - un'istanza del prodotto
     */
    Product getProductInstance(String productID);

    /**
     * Restituisce informazioni complete (ID, nome, descrizione, prezzo) del prodotto associato all'ID dato.
     * @param productID - l'ID del prodotto selezionato
     * @return - array di stringhe, contenente gli attributi del prodotto
     */
    String[] getProductFullInformation(String productID);


}
