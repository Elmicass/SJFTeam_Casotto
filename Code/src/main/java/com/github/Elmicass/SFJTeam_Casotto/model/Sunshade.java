package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.IOException;
import java.util.Objects;

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

import com.google.zxing.WriterException;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Sunshade")
@NoArgsConstructor
public class Sunshade {

    public enum SunshadeType {
        Small,
        Medium,
        Large;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Count")
	private Integer count;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String ID;

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
    private BeachPlace currentlyUsedIn;

    @OneToOne
    @JoinColumn(name = "QrCode", referencedColumnName = "ID")
    private QrCode qrCode;

    public Sunshade(SunshadeType type, BeachPlace beachPlace, PriceList priceList) throws IllegalArgumentException, WriterException, IOException {
        this.ID = String.valueOf(count);
        setType(type);
        setCurrentlyUsedIn(beachPlace);
        setPriceList(priceList);
        setHourlyPrice(); 
        setQrCode();
    }

    public String getID() {
        return ID;
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
        return currentlyUsedIn;
    }

    public void setCurrentlyUsedIn(BeachPlace currentlyUsedIn) {
        Objects.requireNonNull(currentlyUsedIn,"The associated beach place is null");
        this.currentlyUsedIn = currentlyUsedIn;
    }

    public QrCode getQrCode() {
        return qrCode;
    }

    public void setQrCode() throws WriterException, IOException {
        this.qrCode = Objects.requireNonNull(new QrCode(this),"The created QrCode is null");
    }

    


    

}
