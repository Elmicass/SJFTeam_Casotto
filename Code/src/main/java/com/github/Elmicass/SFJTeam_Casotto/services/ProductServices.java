package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Product;
import com.github.Elmicass.SFJTeam_Casotto.repository.IProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductServices implements IProductServices {

    @Autowired
    private IProductsRepository repository;

    @Override
    public Product getInstance(String id) throws EntityNotFoundException {
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
    public boolean createProduct(String name, String description, double unitPrice, int quantity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getProductQuantity(String productID) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getProductsNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String[] getProductInformations(String productID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getProductsInformation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addProduct(String productID, int quantity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean subtractProduct(String productID, int quantity) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
