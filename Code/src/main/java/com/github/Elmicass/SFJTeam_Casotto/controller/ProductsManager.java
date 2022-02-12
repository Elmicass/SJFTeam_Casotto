package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;
import com.github.Elmicass.SFJTeam_Casotto.services.IProductServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductsManager implements IProductManager {

    @Autowired
    private IProductServices services;

    @Override
    public Product getInstance(String productID) {
        return services.getInstance(productID);
    }

    @Override
    public List<Product> getAll() {
        return services.getAll();
    }

    @Override
    public boolean createNewProduct(String name, String description, double unitPrice, int quantity) throws AlreadyExistingException {
        return services.createProduct(name, description, unitPrice, quantity);
    }

    @Override
    public boolean addProduct(String productID, int quantity) {
        return services.addProduct(productID, quantity);
    }

    @Override
    public boolean subtractProduct(String productID, int quantity) {
        return services.subtractProduct(productID, quantity);
    }

    @Override
    public int getProductQuantity(String productID) {
        return services.getProductQuantity(productID);
    }

    @Override
    public boolean delete(String productID) {
        return services.delete(productID);
    }

    @Override
    public boolean exists(String productID) {
        return services.exists(productID);
    }

    

    
    
}
