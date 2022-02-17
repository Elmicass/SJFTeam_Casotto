package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;

public interface IProductManager extends EntityManager<Product> {

    boolean createNewProduct(String name, String description, double unitPrice, int quantity) throws AlreadyExistingException;

    List<Product> getAvailableProducts();

    boolean addProduct(Integer productID, int quantity);

    boolean subtractProduct(Integer productID, int quantity);

    int getProductQuantity(Integer productID);

}
