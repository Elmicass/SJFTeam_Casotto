package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Sunbed")
@NoArgsConstructor
public class Sunbed implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @ManyToOne
    @JoinColumn(name = "PriceList", referencedColumnName = "Name")
    private PriceList priceList;

    @Column(name = "HourlyPrice")
    private double hourlyPrice;

    @ManyToOne
    @JoinColumn(name = "BeachPlace", referencedColumnName = "ID")
    private BeachPlace currentlyUsedIn;

    public Sunbed(BeachPlace beachPlace, PriceList priceList) {
        setCurrentlyUsedIn(beachPlace);
        setPriceList(priceList);
        setHourlyPrice();
    }

    public Integer getID() {
        return id;
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
