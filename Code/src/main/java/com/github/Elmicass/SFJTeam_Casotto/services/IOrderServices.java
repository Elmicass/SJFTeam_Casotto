package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.User;

/**
 * Questa interfaccia è responsabile della gestione di tutte le ordinazioni da parte dei clienti nel servizio bar dell'applicazione.
 * Sa restituire un'istanza di qualsiasi ordine mediante il suo ID, può crearne di nuove o eliminare ordinazioni esistenti.
 */
public interface IOrderServices extends EntityServices<Order> {

      /**
     * Crea una nuova ordinazione vuota. L'ordinazione è associata al cliente corrispondente all'email passata.
     * @param customer - email del cliente
     * @return - true se l'ordinazione viene creata con successo, false altrimenti
     */
    Order createOrder(User customer);

    /**
     * Aggiunge un prodotto ad una determinata ordinazione.
     * @param orderID - ID dell'ordine in corso di creazione
     * @param productID - ID del prodotto selezionato da aggiungere all'ordine
     * @param quantity - quantità del prodotto da aggiungere all'ordine
     * @return - true se il prodotto è stato aggiunto correttamente all'ordinazione, false altrimenti
     */
    boolean addProduct(Integer orderID, Integer productID, int quantity);

    /**
     * Rimuove un prodotto da una determinata ordinazione.
     * @param orderID - ID dell'ordine in corso di creazione
     * @param productID - ID del prodotto selezionato da aggiungere all'ordine
     * @return - true se il prodotto è stato rimosso correttamente dall'ordinazione, false altrimenti
     */
    boolean removeProduct(Integer orderID, Integer productID);

    /**
     * 
     * @param order
     * @return
     */
    void checkOrder(Order order);

    /**
     * 
     * @param order
     * @return
     */
    boolean saveOrder(Order order);

}
