package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;
import com.github.Elmicass.SFJTeam_Casotto.repository.IProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class ProductServices implements IProductServices {

    @Autowired
    private IProductsRepository productsRepository;

    @Override
    public Product getInstance(Integer id) throws EntityNotFoundException {
        return productsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No products found with the given id: " + id));
    }

    @Override
    public List<Product> getAll() {
        return productsRepository.findAll();
    }

    @Override
    public List<Product> getAvailableProducts() {
        List<Product> products = new LinkedList<>();
        for (Product pr : getAll()) {
            if (pr.getQuantity() > 0)
                products.add(pr);
        }
        return products;
    }

    @Override
    public boolean createProduct(@NonNull String name, @NonNull String description, @NonNull Double unitPrice, @NonNull Integer quantity) throws AlreadyExistingException {
        Product product = new Product(name, description, unitPrice, quantity);
        if (productsRepository.findByName(name).isPresent())
            throw new AlreadyExistingException("The product you are trying to create already exists, with the same name: " + name);
        productsRepository.save(product);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The product ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The product with ID: " + id + " does not exist");
        productsRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The product ID value is empty");
        return productsRepository.existsById(id);
    }

    @Override
    public int getProductQuantity(Integer productID) {
        return getInstance(productID).getQuantity();
    }

    @Override
    public int getProductsNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean addProduct(Integer productID, Integer quantity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean subtractProduct(Integer productID, Integer quantity) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
