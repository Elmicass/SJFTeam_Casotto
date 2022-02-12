package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.services.IOrderServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrdersManager implements IOrderManager {

    @Autowired
    private IOrderServices services;
  
    @Override
    public Order getInstance(String orderID) {
        return services.getInstance(orderID);
    }

    @Override
    public List<Order> getAll() {
        return services.getAll();
    }

    @Override
    public Order createNewOrder(User customer) {
        return services.createOrder(customer);
    }

    @Override
    public boolean addProduct(String  orderID, String productID, int quantity) {
        return services.addProduct(orderID, productID, quantity);
    }

    @Override
    public boolean removeProduct(String orderID, String productID) {
        return services.removeProduct(orderID, productID);
    }

    @Override
    public boolean delete(String id) {
        return services.delete(id);
    }

    @Override
    public boolean exists(String id) {
        return services.exists(id);
    }

    @Override
    public void checkOrder(Order order) {
        services.checkOrder(order);
    }


    
    
}
