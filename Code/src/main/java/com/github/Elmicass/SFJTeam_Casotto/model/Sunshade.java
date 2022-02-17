package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Sunshade")
@NoArgsConstructor
public class Sunshade implements Serializable {

    public enum SunshadeType {
        Small,
        Medium,
        Large;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    private SunshadeType type;

    @ManyToOne
    @JoinColumn(name = "PriceList", referencedColumnName = "Name")
    private PriceList priceList;

    @Column(name = "HourlyPrice")
    private double hourlyPrice;

    @OneToOne
    @JoinColumn(name = "BeachPlace", referencedColumnName = "ID")
    private BeachPlace beachPlace;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "QrCode", referencedColumnName = "ID")
    private QrCode qrCode;

    public Sunshade(SunshadeType type, BeachPlace beachPlace, PriceList priceList) throws IllegalArgumentException {
        setType(type);
        setCurrentlyUsedIn(beachPlace);
        setPriceList(priceList);
        setHourlyPrice();
        this.qrCode = new QrCode();
    }

    public Integer getID() {
        return id;
    }

    public SunshadeType getType() {
        return type;
    }

    public void setType(SunshadeType type) throws IllegalArgumentException {
        if (Objects.requireNonNull(type, "Sunshade type value is null").toString().isBlank())
			throw new IllegalArgumentException("The sunshade type value is empty");
		this.type = type;
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

    public void setHourlyPrice() throws IllegalArgumentException {
        if (Objects.requireNonNull(type, "Sunshade type value is null").toString().isBlank())
			throw new IllegalArgumentException("The sunshade type value is empty");
        Objects.requireNonNull(priceList, "Price list reference is null");
        this.hourlyPrice = this.priceList.getSunshadeHourlyPrice(type);
    }

    public BeachPlace getCurrentlyUsedIn() {
        return beachPlace;
    }

    public void setCurrentlyUsedIn(BeachPlace currentlyUsedIn) {
        Objects.requireNonNull(currentlyUsedIn,"The associated beach place is null");
        this.beachPlace = currentlyUsedIn;
    }

    public QrCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(QrCode qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((beachPlace == null) ? 0 : beachPlace.hashCode());
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
        Sunshade other = (Sunshade) obj;
        if (beachPlace == null) {
            if (other.beachPlace != null)
                return false;
        } else if (!beachPlace.equals(other.beachPlace))
            return false;
        return true;
    }

    

    


    

}
