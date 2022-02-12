package com.github.Elmicass.SFJTeam_Casotto.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Order")
@NoArgsConstructor
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Count")
	private Integer count;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String ID;

    @ManyToOne
    @JoinColumn(name = "UserEmail", referencedColumnName = "Email")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "UserCurrentSunshade", referencedColumnName = "String")
    private QrCode orderQrCode;

    @OneToMany
    @JoinColumn(name = "Products")
    private List<Product> products;

    @Column(name = "OrderDueAmount")
    private Double dueAmount;

    @Column(name = "CreationTime")
    private LocalDateTime creationTime;

    @Column(name = "IsOpen")
    private boolean open;

    public Order(User customer) {
        this.ID = String.valueOf(count);
        setCustomer(customer);
        this.products = new LinkedList<Product>();
        this.dueAmount = 0.00;
        this.creationTime = LocalDateTime.now();
        this.open = true;
    }

    public String getID() {
        return ID;
    }

    public User getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public boolean isOpen() {
        return open;
    }

    public void close() {
        this.open = false;
    }

    public void setCustomer(User customer) {
        if (Objects.requireNonNull(customer.getEmail(), "The referenced customer email is null.").isBlank())
            throw new IllegalArgumentException("The referenced customer's email is empty.");
        this.customer = customer;
    }

    public boolean addProduct(Product product, Integer productQuantity) throws IllegalStateException {
        if (Objects.requireNonNull(product, "The product has null value.").getQuantity() == 0)
            throw new IllegalStateException("The product you are trying to add at the order is no longer available.");
        if (product.getQuantity() < productQuantity)
            throw new IllegalStateException("There is not enough product availability to satisfy your demand. Maximum product availability: " + product.getQuantity());
        for (int p = 0; p < productQuantity; p++) {
            this.products.add(product);
            refreshDueAmount();
        }
        return true;
    }

    public boolean removeProduct(Product product) throws IllegalStateException {
        if (!(this.products.contains(Objects.requireNonNull(product, "The product has null value."))))
            throw new IllegalStateException(
                    "The product you are trying to remove from your order is already not present.");
        this.products.removeIf(prod -> Objects.equals(prod, product));
        refreshDueAmount();
        return true;
    }

    public void refreshDueAmount() {
        double total = 0.00;
        for (Product product : this.products) {
            total = total + product.getUnitPrice();
        }
        this.dueAmount = total;
    }

}
