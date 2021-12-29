package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Product;
import com.github.Elmicass.SFJTeam_Casotto.services.IProductServices;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductsManager implements IProductManager {

    @Autowired
    private IProductServices services;

    @Override
    public Product getInstance(String productID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean createNewProduct(String name, String description, double unitPrice, int quantity) {
        // TODO
        return false;
    }

    @Override
    public String[] getProductsInformations() {
        // TODO
        return null;
    }

    @Override
    public String[] getProductInformations(String productID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addProduct(String productID, int quantity) {
        // TODO
        return false;
    }

    @Override
    public boolean subtractProduct(String productID, int quantity) {
        // TODO
        return false;
    }

    @Override
    public int getProductQuantity(String productID) {
        // TODO
        return 0;
    }

    @Override
    public boolean delete(String productID) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean exists(String productID) {
        // TODO Auto-generated method stub
        return false;
    }

    

    
    
}
