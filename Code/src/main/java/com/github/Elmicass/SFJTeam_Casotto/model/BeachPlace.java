package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;
import com.google.zxing.WriterException;

@Entity
@Table(name = "BeachPlace")
public class BeachPlace {

    protected static final AtomicInteger count = new AtomicInteger(0);

	@Id
    @Column(name = "ID")
	private final String ID;

    private int seaRowNumber;

    @OneToOne(mappedBy = "currentlyUsedIn")
    private Sunshade sunshade;

    @Enumerated(EnumType.STRING)
    private SunshadeType type;

    @OneToMany(mappedBy = "currentlyUsedIn")
    private Set<Sunbed> sunbeds;

    private int sunbedsNumber;

    @OneToMany(mappedBy = "entityID")
    private SortedSet<Reservation> reservations;

    public BeachPlace(int seaRowNumber, String sunshadeType, int sunbedsNumber) throws IllegalArgumentException, WriterException, IOException {
        this.ID = String.valueOf(count.getAndIncrement());
        this.reservations = new TreeSet<Reservation>();
        setSeaRowNumber(seaRowNumber);
        setType(sunshadeType);
        setSunshade(this.type);
        setSunbedsNumber(sunbedsNumber);
        setSunbeds(this.sunbedsNumber);
    }

    public String getID() {
        return ID;
    }

    public int getSeaRowNumber() {
        return seaRowNumber;
    }

    public void setSeaRowNumber(int seaRowNumber) throws IllegalArgumentException {
        if (Objects.requireNonNull(seaRowNumber, "Sea row value is null").intValue() == 0)
			throw new IllegalArgumentException("The sea row number value is 0");
		this.seaRowNumber = seaRowNumber;
    }

    public Sunshade getSunshade() {
        return sunshade;
    }

    public void setSunshade(SunshadeType type) throws IllegalArgumentException, WriterException, IOException {
        if (Objects.requireNonNull(type,"Sunshade type value is null").toString().isBlank())
            throw new IllegalArgumentException("The sunshade type value is empty");
        Sunshade sunshade = new Sunshade(type, this);
        this.sunshade = sunshade;
    }

    public SunshadeType getType() {
        return type;
    }

    public void setType(String typeStringName) throws IllegalArgumentException {
        SunshadeType type = SunshadeType.valueOf(typeStringName);
        switch (type) {
            case Small:
                this.type = SunshadeType.Small;
                break;
            case Medium:
                this.type = SunshadeType.Medium;
                break;
            case Large:
                this.type = SunshadeType.Large;
                break;
            default:
                throw new IllegalArgumentException(
                        "The sunshade type you are trying to create is not one of: " + SunshadeType.values() + ".");
        }
    }

    public Set<Sunbed> getSunbeds() {
        return sunbeds;
    }

    public void setSunbeds(int sunbedsNumber) {
        for (int s = 0; s < sunbedsNumber; s++) {
            Sunbed bed = Objects.requireNonNull(new Sunbed(this), "The created sunbed is null");
            this.sunbeds.add(bed);
        }
    }

    public int getSunbedsNumber() {
        return sunbedsNumber;
    }

    public void setSunbedsNumber(int sunbedsNumber) {
        if (Objects.requireNonNull(sunbedsNumber, "Sunbeds number value is null").intValue() == 0)
			throw new IllegalArgumentException("The sunbeds number value is 0");
		this.sunbedsNumber = sunbedsNumber;
    }

    public SortedSet<Reservation> getReservations() {
        return reservations;
    }

    public boolean addReservation(Reservation res) throws IllegalStateException {
        if (reservations.contains(res))
            throw new IllegalStateException("The user is already booked for this beach place");
        return reservations.add(Objects.requireNonNull(res, "The reservation is null"));
    }

    public boolean removeReservation(Reservation res) throws IllegalStateException {
        if (!(this.reservations.contains(Objects.requireNonNull(res, "The booking has null value"))))
            throw new IllegalStateException(
                    "The booking you are trying to cancel does not exist");
        this.reservations.removeIf(r -> Objects.equals(r, res));
        return true;
    }
    

    









}
