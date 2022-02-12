package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.User;

public interface IOrderManager extends EntityManager<Order, String> {

    Order createNewOrder(User customer);

    boolean addProduct(String orderID, String productID, int quantity);
   
    boolean removeProduct(String orderID, String productID);

    void checkOrder(Order order);

}
