package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "PriceList")
@NoArgsConstructor
public class PriceList implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "SingleSunbedHourlyPrice")
    private double SingleSunbedHourlyPrice;

    @Column(name = "SmallSunshadeHourlyPrice")
    private double SmallSunshadeHourlyPrice;

    @Column(name = "MediumSunshadeHourlyPrice")
    private double MediumSunshadeHourlyPrice;

    @Column(name = "LargeSunshadeHourlyPrice")
    private double LargeSunshadeHourlyPrice;

    public PriceList(String name, double sunbedHourly, double smallSunSHourly, double medSunSHourly, double largeSunSHourly) throws IllegalArgumentException {
        setName(name);
        setSingleSunbedHourlyPrice(sunbedHourly);
        setSmallSunshadeHourlyPrice(smallSunSHourly);
        setMediumSunshadeHourlyPrice(medSunSHourly);
        setLargeSunshadeHourlyPrice(largeSunSHourly);
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.requireNonNull(name, "Name value is null").isBlank())
            throw new IllegalArgumentException("The price list name is empty");
        this.name = name;
    }

    public double getSingleSunbedHourlyPrice() {
        return SingleSunbedHourlyPrice;
    }

    public void setSingleSunbedHourlyPrice(Double singleSunbedHourlyPrice) throws IllegalArgumentException {
        if  (Objects.requireNonNull(singleSunbedHourlyPrice, "The price number value is null").doubleValue() < 0.00)
            throw new IllegalArgumentException("The sunbed hourly price value is less than zero");
        this.SingleSunbedHourlyPrice = singleSunbedHourlyPrice;
    }

    public double getSunshadeHourlyPrice(SunshadeType type) throws IllegalArgumentException {
        switch (type) {
            case Small:
                return getSmallSunshadeHourlyPrice();
            case Medium:
                return getMediumSunshadeHourlyPrice();
            case Large:
                return getLargeSunshadeHourlyPrice();
            default:
                throw new IllegalArgumentException(
                "The sunshade type you are requesting the price for is not one of: " + SunshadeType.values() + ".");
        }
    }

    public double getSmallSunshadeHourlyPrice() {
        return SmallSunshadeHourlyPrice;
    }

    public void setSmallSunshadeHourlyPrice(Double smallSunshadeHourlyPrice) throws IllegalArgumentException {
        if  (Objects.requireNonNull(smallSunshadeHourlyPrice, "The price number value is null").doubleValue() < 0.00)
            throw new IllegalArgumentException("The sunshade (type Small) hourly price value is less than zero");
        this.SmallSunshadeHourlyPrice = smallSunshadeHourlyPrice;
    }

    public double getMediumSunshadeHourlyPrice() {
        return MediumSunshadeHourlyPrice;
    }

    public void setMediumSunshadeHourlyPrice(double mediumSunshadeHourlyPrice) throws IllegalArgumentException {
        if  (Objects.requireNonNull(mediumSunshadeHourlyPrice, "The price number value is null").doubleValue() < 0.00)
            throw new IllegalArgumentException("The sunshade (type Medium) hourly price value is less than zero");
        this.MediumSunshadeHourlyPrice = mediumSunshadeHourlyPrice;
    }

    public double getLargeSunshadeHourlyPrice() {
        return LargeSunshadeHourlyPrice;
    }

    public void setLargeSunshadeHourlyPrice(double largeSunshadeHourlyPrice) throws IllegalArgumentException {
        if  (Objects.requireNonNull(largeSunshadeHourlyPrice, "The price number value is null").doubleValue() < 0.00)
            throw new IllegalArgumentException("The sunshade (type Large) hourly price value is less than zero");
        this.LargeSunshadeHourlyPrice = largeSunshadeHourlyPrice;
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
        PriceList other = (PriceList) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    

    






    
}
