package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;
import com.github.Elmicass.SFJTeam_Casotto.repository.IProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class ProductServices implements IProductServices {

    @Autowired
    private IProductsRepository productsRepository;

    @Override
    public Product getInstance(String id) throws EntityNotFoundException {
        return productsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No products found with the given id: " + id));
    }

    @Override
    public boolean createProduct(@NonNull String name, @NonNull String description, @NonNull double unitPrice, @NonNull int quantity) throws AlreadyExistingException {
        Product product = new Product(name, description, unitPrice, quantity);
        if (productsRepository.findByName(name).equals(product))
            throw new AlreadyExistingException("The product you are trying to create already exists, with the same name: " + name);
        productsRepository.save(product);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The product ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The product with ID: " + id + " does not exist");
        productsRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The product ID value is empty");
        return productsRepository.existsById(id);
    }

    @Override
    public int getProductQuantity(String productID) {
        return getInstance(productID).getQuantity();
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
