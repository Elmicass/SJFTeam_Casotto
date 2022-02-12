package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@NoArgsConstructor
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Count")
	private Integer count;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String ID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "UnitPrice")
    private Double unitPrice;

    @Column(name = "Quantity")
    private Integer quantity;

    public Product(String name, String description, double unitPrice, int quantity) throws IllegalArgumentException {
        this.ID = String.valueOf(count);
        setName(name);
        setDescription(description);
        setUnitPrice(unitPrice);
        setQuantity(quantity);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.requireNonNull(name, "Name value is null").isBlank())
            throw new IllegalArgumentException("The product name is empty");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (Objects.requireNonNull(description, "Description value is null").isBlank())
            throw new IllegalArgumentException("The product description is empty");
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) throws IllegalArgumentException {
        if (Objects.requireNonNull(unitPrice, "The product unit price value is null").doubleValue() < 0.00)
            throw new IllegalArgumentException("The product unit price value is less than zero");
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        if(Objects.requireNonNull(quantity, "The product quantity value is null").intValue() < 0)
            throw new IllegalArgumentException("The product quantity value is less than zero");
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

   
}
