package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;

public interface IProductManager extends EntityManager<Product, String> {

    boolean createNewProduct(String name, String description, double unitPrice, int quantity) throws AlreadyExistingException;

    boolean addProduct(String productID, int quantity);

    boolean subtractProduct(String productID, int quantity);

    int getProductQuantity(String productID);

}
