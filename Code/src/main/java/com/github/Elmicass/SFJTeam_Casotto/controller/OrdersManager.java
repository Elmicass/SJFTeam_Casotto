package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.services.IOrderServices;

import org.springframework.beans.factory.annotation.Autowired;

public class OrdersManager implements IOrderManager {

    @Autowired
    private IOrderServices services;
  
    @Override
    public Order getInstance(String orderID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean createNewOrder(String customerEmail) {
        // TODO
        return true;

    }

    @Override
    public boolean addProduct(String  orderID, String productID) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean removeProduct(String orderID, String productID) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean exists(String id) {
        // TODO Auto-generated method stub
        return false;
    }


    
    
}
