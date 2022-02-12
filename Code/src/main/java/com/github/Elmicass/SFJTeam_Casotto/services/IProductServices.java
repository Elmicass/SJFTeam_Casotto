package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;

/**
 * Questa interfaccia è responsabile della gestione di tutti i prodotti offerti dallo chalet nel servizio bar dell'applicazione.
 * Sa restituire un'istanza di qualsiasi prodotto mediante il suo ID, può crearne di nuovi, o eliminare prodotti esistenti. 
 */
public interface IProductServices extends EntityServices<Product, String>{

    /**
     * 
     * @param name
     * @param description
     * @param unitPrice
     * @param quantity
     * @return
     * @throws AlreadyExistingException
     */
    boolean createProduct(String name, String description, double unitPrice, int quantity) throws AlreadyExistingException;

    /**
     * 
     * @param productID
     * @return
     */
    int getProductQuantity(String productID);

    /**
     * 
     * @return
     */
    int getProductsNumber();
    
    /**
     * 
     * @param productID
     * @param quantity
     * @return
     */
    boolean addProduct(String productID, int quantity);
    
    /**
     * 
     * @param productID
     * @param quantity
     * @return
     */
    boolean subtractProduct(String productID, int quantity);


    

}
