package Casotto.model;

/**
 * Rappresenta un singolo prodotto tra quelli offerti nel servizio bar dello chalet.
 */
public interface IProduct {
    
    /**
     * Restituisce l'ID del prodotto.
     * @return - ID
     */
    String getID();

    /**
     * Restituisce il nome del prodotto.
     * @return - nome
     */
    String getName();

    /**
     * Restituisce la descrizione del prodotto.
     * @return - descrizione
     */
    String getDescription();

    /**
     * Restituisce il prezzo unitario del prodotto.
     * @return - numero double rappresentate il prezzo
     */
    double getUnitPrice();

    /**
     * Restituisce la quantità di prodotto disponibile in magazzino.
     * @return - numero intero rappresentante la quantità
     */
    int getQuantity();

    /**
     * Restituisce informazioni base (nome, prezzo) del prodotto.
     * @param product - prodotto 
     * @return - array di stringhe contenente gli attributi del prodotto 
     */
    String[] getProductBasicInformations(Product product);

    /**
     * Restituisce informazioni complete (ID, nome, descrizione, prezzo) del prodotto.
     * @param product - prodotto 
     * @return - array di stringhe contenente gli attributi del prodotto 
     */
    String[] getProductFullInformation(Product product);



}