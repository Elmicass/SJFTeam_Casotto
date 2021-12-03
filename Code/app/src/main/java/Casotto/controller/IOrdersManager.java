package Casotto.controller;

import Casotto.model.*;

/**
 * TODO
 */
public interface IOrdersManager {

    /**
     * TODO
     * @param orderID
     * @return
     */
    Order getOrderInstance(String orderID);

    /**
     * TODO
     * @param clientEmail
     * @return
     */
    boolean openNewOrder(String clientEmail);

    /**
     * TODO
     * @param productID
     * @return
     */
    boolean addProduct(String productID);

    /**
     * TODO
     * @param productID
     * @param productQuantity
     * @return
     */
    boolean addProduct(String productID, int productQuantity);

    /**
     * TODO
     * @param productID
     * @return
     */
    boolean removeProduct(String productID);



}
