package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.repository.IOrdersRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class OrderServices implements IOrderServices {

    @Autowired
    private IOrdersRepository ordersRepository;

    @Autowired
    private IProductsRepository productsRepository;

    @Override
    public Order getInstance(String id) throws EntityNotFoundException {
        return ordersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No order found with the given id: " + id));
    }

    @Override
    public List<Order> getAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Order createOrder(@NonNull User customer) {
        Order order = new Order(customer);
        return order;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The order ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The order with ID: " + id + " does not exist");
        ordersRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The order ID value is empty");
        return ordersRepository.existsById(id);
    }

    @Override
    public boolean addProduct(String orderID, String productID, int quantity) {
        boolean result = getInstance(orderID).addProduct(productsRepository.getById(productID), quantity);
        ordersRepository.save(getInstance(orderID));
        return result;
    }

    @Override
    public boolean removeProduct(String orderID, String productID) {
        boolean result = getInstance(orderID).removeProduct(productsRepository.getById(productID));
        ordersRepository.save(getInstance(orderID));
        return result;
    }

    @Override
    public void checkOrder(Order order) {
        if (order.getProducts().isEmpty())
            delete(order.getID());
        else saveOrder(order);
    }

    @Override
    public boolean saveOrder(Order order) {
        ordersRepository.save(order);
        return true;
    }
    
    
}
