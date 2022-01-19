package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Sunbed")
public class Sunbed {

    @Transient
    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    @ManyToOne
    @JoinColumn(name = "PriceList", referencedColumnName = "Name")
    private PriceList priceList;

    @Column(name = "HourlyPrice")
    private double hourlyPrice;

    @ManyToOne
    @JoinColumn(name = "BeachPlace", referencedColumnName = "ID")
    private BeachPlace currentlyUsedIn;

    public Sunbed(BeachPlace beachPlace, PriceList priceList) {
        this.ID = String.valueOf(count.incrementAndGet());
        setCurrentlyUsedIn(beachPlace);
        setPriceList(priceList);
        setHourlyPrice();
    }

    public String getID() {
        return ID;
    }

    public BeachPlace getCurrentlyUsedIn() {
        return currentlyUsedIn;
    }

    public void setCurrentlyUsedIn(BeachPlace currentlyUsedIn) {
        Objects.requireNonNull(currentlyUsedIn,"The associated beach place is null");
        this.currentlyUsedIn = currentlyUsedIn;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        Objects.requireNonNull(priceList,"The associated price list is null");
        this.priceList = priceList;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice() {
        Objects.requireNonNull(priceList, "Price list reference is null");
        this.hourlyPrice = this.priceList.getSingleSunbedHourlyPrice();
    }

    
    


    
}
