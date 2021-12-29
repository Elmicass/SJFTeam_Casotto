package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;

public interface IOrderManager extends EntityManager<Order, String> {

    boolean createNewOrder(String customerEmail);

    boolean addProduct(String orderID, String productID);
   
    boolean removeProduct(String orderID, String productID);

}
