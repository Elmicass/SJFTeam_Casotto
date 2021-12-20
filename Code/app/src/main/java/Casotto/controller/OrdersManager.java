package Casotto.controller;

import Casotto.model.Customer;
import Casotto.model.Order;
import Casotto.model.Product;

public class OrdersManager implements IOrdersManager {

  
    @Override
    public Order getOrderInstance(String orderID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean openNewOrder(Customer customer) {
        Order order = new Order();
        return true;

    }

    @Override
    public boolean addProduct(Product product, Integer productQuantity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addProduct(Order order, Product product) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeProduct(Order order, Product product) {
        // TODO Auto-generated method stub
        return false;
    }


    
    
}
