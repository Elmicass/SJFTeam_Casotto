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
     * @param customer - oggetto di tipo cliente
     * @return - true se l'ordinazione viene creata con successo, false altrimenti
     */
    boolean openNewOrder(Customer customer);

    /**
     * Aggiunge un prodotto ad una determinata ordinazione.
     * @param order - oggetto ordine in corso di creazione
     * @param product - oggetto prodotto selezionato
     * @return - true se il prodotto è stato aggiunto correttamente all'ordinazione, false altrimenti
     */
    boolean addProduct(Order order, Product product);

    /**
     * Aggiunge la quantità di prodotto scelta ad una determinata ordinazione.
     * @param product - l'oggetto prodotto selezionato
     * @param productQuantity - quantità del prodotto selezionata
     * @return - true se la quantità di prodotto è stata aggiunta correttamente all'ordinazione, false altrimenti
     */
    boolean addProduct(Product product, Integer productQuantity);

    /**
     * Rimuove un prodotto da una determinata ordinazione.
     * @param order - l'oggetto ordine in corso di creazione
     * @param product - l'oggetto prodotto selezionato
     * @return - true se il prodotto è stato rimosso correttamente dall'ordinazione, false altrimenti
     */
    boolean removeProduct(Order order, Product product);



}
