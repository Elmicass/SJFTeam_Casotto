package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;
import com.google.zxing.WriterException;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "BeachPlace")
@NoArgsConstructor
public class BeachPlace implements Comparable<BeachPlace>, IEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Count")
	private Integer count;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String ID;

    @ManyToOne
    @JoinColumn(name = "SeaRow", referencedColumnName = "SeaRowNumber")
    private SeaRow seaRow;

    @Column(name = "SeaRowPosition")
    private int seaRowPosition;

    @Column(name = "SeaRowPrice")
    private double seaRowFixedPrice;

    @OneToOne(mappedBy = "currentlyUsedIn")
    @JoinColumn(name = "Sunshade")
    private Sunshade sunshade;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private SunshadeType type;

    @OneToMany(mappedBy = "currentlyUsedIn")
    @Column(name = "Sunbeds")
    private Set<Sunbed> sunbeds;

    @Column(name = "SunbedsNumber")
    private int sunbedsNumber;

    @Column(name = "HourlyPrice")
    private double hourlyPrice;

    @OneToMany(mappedBy = "bpReference")
    @Column(name = "Reservations")
    @OrderBy("Timeslot ASC, UserEmail ASC")
    private SortedSet<Reservation> reservations;

    public BeachPlace(SeaRow seaRow, int position, PriceList priceList, String sunshadeType, int sunbedsNumber)
            throws IllegalArgumentException, WriterException, IOException, IllegalStateException, ReachedLimitOfObjects {
        this.ID = String.valueOf(count);
        this.reservations = new TreeSet<Reservation>();
        setSeaRow(seaRow);
        setPosition(position);
        setSeaRowFixedPrice();
        setType(sunshadeType);
        setSunshade(this.type, priceList);
        setSunbedsNumber(sunbedsNumber);
        setSunbeds(this.sunbedsNumber, priceList);
        this.seaRow.addBeachPlace(this);
    }

    public String getID() {
        return ID;
    }

    public SeaRow getSeaRow() {
        return seaRow;
    }

    public void setSeaRow(SeaRow seaRow) throws IllegalArgumentException {
        if (Objects.requireNonNull(seaRow, "Sea row value is null.").getSeaRowNumber() == 0)
            throw new IllegalArgumentException("The sea row number value is 0.");
        this.seaRow = seaRow;
    }

    public int getPosition() {
        return seaRowPosition;
    }

    public void setPosition(int position) {
        if (Objects.requireNonNull(position, "Position value is null.").intValue() > seaRow.getMaxBeachPlacesInThisRow()-1)
            throw new IllegalArgumentException("The given position value is not valid. Valid values go from 0 to " + (seaRow.getMaxBeachPlacesInThisRow()-1) + ".");
        this.seaRowPosition = position;
    
    }

    public double getSeaRowFixedPrice() {
        return seaRowFixedPrice;
    }

    public void setSeaRowFixedPrice() {
        Objects.requireNonNull(getSeaRow().getFixedPrice(), "The sea row fixed price is null.");
        this.seaRowFixedPrice = getSeaRow().getFixedPrice();
    }

    public Sunshade getSunshade() {
        return sunshade;
    }

    public void setSunshade(SunshadeType type, PriceList priceList)
            throws IllegalArgumentException, WriterException, IOException {
        if (Objects.requireNonNull(type, "Sunshade type value is null.").toString().isBlank())
            throw new IllegalArgumentException("The sunshade type value is empty.");
        Sunshade sunshade = new Sunshade(type, this, priceList);
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

    public void setSunbeds(int sunbedsNumber, PriceList priceList) {
        for (int s = 0; s < sunbedsNumber; s++) {
            Sunbed bed = Objects.requireNonNull(new Sunbed(this, priceList), "The created sunbed is null.");
            this.sunbeds.add(bed);
        }
    }

    public int getSunbedsNumber() {
        return sunbedsNumber;
    }

    public void setSunbedsNumber(int sunbedsNumber) {
        if (Objects.requireNonNull(sunbedsNumber, "Sunbeds number value is null.").intValue() == 0)
            throw new IllegalArgumentException("The sunbeds number value is 0.");
        this.sunbedsNumber = sunbedsNumber;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(PriceList priceList) {
        double sunshadeHourlyPrice = sunshade.getHourlyPrice();
        Sunbed sunbed = Objects.requireNonNull(new Sunbed(this, priceList), "The created sunbed is null.");
        double sunbedHourlyPrice = sunbed.getHourlyPrice();
        Objects.requireNonNull(sunshadeHourlyPrice, "The associated sunshade's hourly price is null.");
        Objects.requireNonNull(sunbedHourlyPrice, "The associated sunbeds's hourly price is null.");
        this.hourlyPrice = sunshadeHourlyPrice + sunbedHourlyPrice;
    }

    public SortedSet<Reservation> getReservations() {
        return reservations;
    }
    
    public boolean isFree(TimeSlot timeSlot) {
        Iterator<Reservation> free = reservations.iterator();
        while (free.hasNext()) {
            if (free.next().getEntityObject().equals(this) && free.next().getTimeSlot().overlapsWith(Objects.requireNonNull(timeSlot,"The given timeslot is null.")))
                return false;
        }
        return true;
    }

    @Override
    public boolean addReservation(Reservation res) throws IllegalStateException {
        if (isFree(res.getTimeSlot()))
            return reservations.add(Objects.requireNonNull(res, "The reservation is null."));
        else
            throw new IllegalStateException(
                    "This beach place is already booked for the time slot provided.");   
    }

    @Override
    public boolean removeReservation(Reservation res) throws IllegalStateException {
        if (!(this.reservations.contains(Objects.requireNonNull(res, "The booking has null value."))))
            throw new IllegalStateException(
                    "The booking you are trying to cancel does not exist for this beach place.");
        this.reservations.removeIf(r -> Objects.equals(r, res));
        return true;
    }

    public boolean addSunbed(PriceList priceList) {
        Objects.requireNonNull(priceList, "Beach place's associated price list is null.");
        Sunbed bed = new Sunbed(this, priceList);
        return sunbeds.add(bed);
    }

    public boolean removeSunbed(Sunbed sunbed) throws IllegalArgumentException {
        if (!(this.sunbeds.contains(Objects.requireNonNull(sunbed, "The sunbed is null."))))
            throw new IllegalArgumentException(
                    "The sunbed you are trying to remove does not exist in this beach place.");
        this.sunbeds.removeIf(s -> Objects.equals(s, sunbed));
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((seaRow == null) ? 0 : seaRow.hashCode());
        result = prime * result + seaRowPosition;
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
        BeachPlace other = (BeachPlace) obj;
        if (seaRow == null) {
            if (other.seaRow != null)
                return false;
        } else if (!seaRow.equals(other.seaRow))
            return false;
        if (seaRowPosition != other.seaRowPosition)
            return false;
        return true;
    }

    @Override
    public int compareTo(BeachPlace bp) {
        Objects.requireNonNull(bp, "The passed beach place is null.");
		if (this.seaRow.equals(bp.seaRow)) {
			return ((Integer)this.seaRowPosition).compareTo(bp.seaRowPosition);
		} else {
			return ((Integer)this.seaRow.getSeaRowNumber()).compareTo(bp.seaRow.getSeaRowNumber());
		}
    }

}
