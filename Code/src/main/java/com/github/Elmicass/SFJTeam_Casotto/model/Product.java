package com.github.Elmicass.SFJTeam_Casotto.model;

public class Product {
    private String ID;
    private String name;
    private String description;
    private Double unitPrice;
    private Integer quantity;

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice){
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public String[] getProductBasicInformations(Product product) {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getProductFullInformation(Product product) {
        // TODO Auto-generated method stub
        return null;
    }




    
}
