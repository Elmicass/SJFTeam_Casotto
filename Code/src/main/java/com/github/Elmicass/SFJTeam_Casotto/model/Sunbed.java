package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Sunbed")
public class Sunbed {

    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    @ManyToOne
    @JoinColumn(name = "BeachPlace", referencedColumnName = "ID")
    private BeachPlace currentlyUsedIn;

    public Sunbed(BeachPlace beachPlace) {
        this.ID = String.valueOf(count.getAndIncrement());
        setCurrentlyUsedIn(beachPlace);
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

    


    
}
