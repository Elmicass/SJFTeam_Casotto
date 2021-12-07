package Casotto.controller;

import Casotto.model.*;

/**
 * Questa interfaccia ha la responsabilità di gestire le ordinazioni da parte dei clienti nel servizio bar dell'applicazione. Sa restituire un'istanza di qualsiasi ordine 
 * mediante il suo ID, può crearne di nuove o elimiinare ordinazioni esistenti.
 */
public interface IOrdersManager {

    /**
     * Restituisce un'istanza dell'ordine associato all'ID dato.
     * @param ID - l'ID dell'ordine ricercato
     * @return un'istanza dell'ordine
     */
    Order getOrderInstance(String orderID);

    /**
     * Crea una nuova ordinazione vuota. L'ordinazione è associata al cliente corrispondente all'email passata.
     * @param clientEmail - email del cliente
     * @return - true se l'ordinazione viene creata con successo, false altrimenti
     */
    boolean openNewOrder(String clientEmail);

    /**
     * Aggiunge un prodotto ad una determinata ordinazione.
     * @param orderID - l'ID dell'ordinazione in corso di creazione
     * @param productID - l'ID del prodotto selezionato
     * @return - true se il prodotto è stato aggiunto correttamente all'ordinazione, false altrimenti
     */
    boolean addProduct(String orderID, String productID);

    /**
     * Aggiunge la quantità di prodotto scelta ad una determinata ordinazione.
     * @param productID - l'ID del prodotto selezionato
     * @param productQuantity - quantità del prodotto selezionata
     * @return - true se la quantità di prodotto è stata aggiunta correttamente all'ordinazione, false altrimenti
     */
    boolean addProduct(String productID, int productQuantity);

    /**
     * Rimuove un prodotto da una determinata ordinazione.
     * @param orderID - l'ID dell'ordinazione in corso di creazione
     * @param productID - l'ID del prodotto selezionato
     * @return - true se il prodotto è stato rimosso correttamente dall'ordinazione, false altrimenti
     */
    boolean removeProduct(String orderID, String productID);



}
