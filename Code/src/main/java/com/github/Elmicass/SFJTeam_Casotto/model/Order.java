package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.List;

public class Order {
    private String ID;
    private Customer customer;
    //private QrCode qrcode;
    private List<Product> product;
    private Double dueAmount;


    public String getID() {
        return ID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return product;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public boolean addProduct(Product product, Integer productQuantity) {
        // TODO
        return false;
    }

    public boolean rimuoviProdotto(Product product) {
        // TODO Auto-generated method stub
        return false;
    }

    
    
}
