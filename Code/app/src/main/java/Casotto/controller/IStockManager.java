package Casotto.controller;

import Casotto.model.*;

/**
 * TODO
 */
public interface IStockManager {

    /**
     * TODO
     * @return
     */
    Stock getStockInstance();

    /**
     * TODO
     * @param productID
     * @return
     */
    int getProductQuantity(String productID);

    /**
     * TODO
     * @param productID
     * @param quantity
     * @return
     */
    boolean addProduct(String productID, int quantity);
    
    /**
     * TODO
     * @param productID
     * @param quantity
     * @return
     */
    boolean subtractProduct(String productID, int quantity);



}
