package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Customer;
import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.repository.IOrdersRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderServices implements IOrderServices {

    @Autowired
    private IOrdersRepository repository;

    @Override
    public Order getInstance(String id) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
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

    @Override
    public boolean createOrder(Customer customer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addProduct(String orderID, String productID, int quantity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeProduct(String orderID, String productID) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkOrder(Order order) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean saveOrder(Order order) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
