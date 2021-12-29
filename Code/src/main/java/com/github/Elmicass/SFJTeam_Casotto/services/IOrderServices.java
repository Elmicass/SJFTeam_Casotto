package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Customer;
import com.github.Elmicass.SFJTeam_Casotto.model.Order;

/**
 * Questa interfaccia è responsabile della gestione di tutte le ordinazioni da parte dei clienti nel servizio bar dell'applicazione.
 * Sa restituire un'istanza di qualsiasi ordine mediante il suo ID, può crearne di nuove o eliminare ordinazioni esistenti.
 */
public interface IOrderServices extends EntityServices<Order, String> {

      /**
     * Crea una nuova ordinazione vuota. L'ordinazione è associata al cliente corrispondente all'email passata.
     * @param customer - oggetto di tipo cliente
     * @return - true se l'ordinazione viene creata con successo, false altrimenti
     */
    boolean createOrder(Customer customer);

    /**
     * Aggiunge un prodotto ad una determinata ordinazione.
     * @param orderID - ID dell'ordine in corso di creazione
     * @param productID - ID del prodotto selezionato da aggiungere all'ordine
     * @param quantity - quantità del prodotto da aggiungere all'ordine
     * @return - true se il prodotto è stato aggiunto correttamente all'ordinazione, false altrimenti
     */
    boolean addProduct(String orderID, String productID, int quantity);

    /**
     * Rimuove un prodotto da una determinata ordinazione.
     * @param orderID - ID dell'ordine in corso di creazione
     * @param productID - ID del prodotto selezionato da aggiungere all'ordine
     * @return - true se il prodotto è stato rimosso correttamente dall'ordinazione, false altrimenti
     */
    boolean removeProduct(String orderID, String productID);

    /**
     * 
     * @param order
     * @return
     */
    boolean checkOrder(Order order);

    /**
     * 
     * @param order
     * @return
     */
    boolean saveOrder(Order order);

}
