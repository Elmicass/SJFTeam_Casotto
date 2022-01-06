package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.zxing.WriterException;

@Entity
@Table(name = "Sunshad")
public class Sunshade {

    public enum SunshadeType {
        Small,
        Medium,
        Large;
    }

    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    @Enumerated(EnumType.STRING)
    private SunshadeType type;

    @OneToOne
    @JoinColumn(name = "BeachPlace", referencedColumnName = "ID")
    private BeachPlace currentlyUsedIn;

    @OneToOne
    @JoinColumn(name = "QrCode", referencedColumnName = "ID")
    private QrCode qrCode;

    public Sunshade(SunshadeType type, BeachPlace beachPlace) throws WriterException, IOException {
        this.ID = String.valueOf(count.getAndIncrement());
        setType(type);
        setCurrentlyUsedIn(beachPlace);
        setQrCode();
    }

    public String getID() {
        return ID;
    }

    public SunshadeType getType() {
        return type;
    }

    public void setType(SunshadeType type) {
        if (Objects.requireNonNull(type, "Sunshade type value is null").toString().isBlank())
			throw new IllegalArgumentException("The sunshade type value is empty");
		this.type = type;
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
