package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.repository.IOrdersRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderServices implements IOrderServices {

    @Autowired
    private IOrdersRepository ordersRepository;

    @Override
    public Order getInstance(String id) throws EntityNotFoundException {
        return ordersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No order found with the given id: " + id));
    }

    @Override
    public boolean createOrder(User customer) {
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
