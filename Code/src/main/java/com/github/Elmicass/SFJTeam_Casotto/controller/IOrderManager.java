package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.User;

public interface IOrderManager extends EntityManager<Order> {

    Order createNewOrder(User customer);

    boolean addProduct(Integer orderID, Integer productID, int quantity);
   
    boolean removeProduct(Integer orderID, Integer productID);

    void checkOrder(Order order);

}
