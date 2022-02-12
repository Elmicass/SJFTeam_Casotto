package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "SeaRow")
@NoArgsConstructor
public class SeaRow implements Comparable<SeaRow> {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Count")
	private Integer count;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private String ID;

    @Column(name = "SeaRowNumber")
    private Integer seaRowNumber;

    @Column(name = "MaxBPsInThisRow")
    private Integer maxBeachPlacesInThisRow;

    @Column(name = "AvailableBPsPositions") 
    private Integer availableBeachPlacesPositions;

    @Column(name = "SeaRowMap")
    @ElementCollection
    private Map<Integer, BeachPlace> seaRowMap;

    @Column(name = "SeaRowFixedPrice")
    private Double fixedPrice;

    @OneToMany(mappedBy = "seaRow")
    @Column(name = "BeachPlaces")
    private Set<BeachPlace> BeachPlaces;

    public SeaRow(Integer seaRowNumber, Integer maxBPs, Double price) throws IllegalArgumentException, NumberFormatException {
        this.ID = String.valueOf(count);
        setSeaRowNumber(seaRowNumber);
        setMaxBeachPlacesInThisRow(maxBPs);
        setAvailableBeachPlacesPositions(maxBeachPlacesInThisRow);
        this.seaRowMap = new HashMap<>();
        setFixedPrice(price);
    }

    public String getID() {
        return ID;
    }

    public int getSeaRowNumber() {
        return seaRowNumber;
    }

    public void setSeaRowNumber(Integer seaRowNumber) throws IllegalArgumentException {
        if (Objects.requireNonNull(seaRowNumber, "The sea row number value is null").intValue() == 0)
            throw new IllegalArgumentException("The sea row number value is zero");
        this.seaRowNumber = seaRowNumber;
    }

    public int getMaxBeachPlacesInThisRow() {
        return maxBeachPlacesInThisRow;
    }

    public void setMaxBeachPlacesInThisRow(Integer maxBeachPlacesInThisRow) throws IllegalArgumentException {
        if (Objects.requireNonNull(maxBeachPlacesInThisRow, "The number value is null").intValue() == 0)
            throw new IllegalArgumentException("The number value is zero");
        this.maxBeachPlacesInThisRow = maxBeachPlacesInThisRow;
    }

    public int getAvailableBeachPlacesPositions() {
        return availableBeachPlacesPositions;
    }

    public void setAvailableBeachPlacesPositions(Integer availableBeachPlacesPositions) {
        Objects.requireNonNull(availableBeachPlacesPositions, "The number value is null");
        this.availableBeachPlacesPositions = availableBeachPlacesPositions;
    }

    public double getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(Double fixedPrice) throws NumberFormatException {
        if (Objects.requireNonNull(fixedPrice, "The price value is null").doubleValue() <= 0.00)
            this.fixedPrice = 0.00;
        this.fixedPrice = fixedPrice;
    }

    public Map<Integer, BeachPlace> getSeaRowMap() {
        return seaRowMap;
    }

    public void setSeaRowMap(Map<Integer, BeachPlace> map) {
        this.seaRowMap = map;
    }

    public Set<BeachPlace> getBeachPlaces() {
        return BeachPlaces;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + seaRowNumber;
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
        SeaRow other = (SeaRow) obj;
        if (seaRowNumber != other.seaRowNumber)
            return false;
        return true;
    }
    
    @Override
    public int compareTo(SeaRow sr) {
        Objects.requireNonNull(sr, "The passed sea row is null.");
		return this.seaRowNumber.compareTo(sr.seaRowNumber);
    }
    
    public boolean addBeachPlace(BeachPlace beachPlace) throws IllegalStateException, ReachedLimitOfObjects {
        if (BeachPlaces.contains(beachPlace))
            throw new IllegalStateException("This sea row already contains the given beach place");
        if (availableBeachPlacesPositions > 0) {
            if (!seaRowMap.containsKey(beachPlace.getPosition())) {
                if (BeachPlaces.add(Objects.requireNonNull(beachPlace, "The beach place is null"))) {
                    this.seaRowMap.put(beachPlace.getPosition(), beachPlace);
                    this.availableBeachPlacesPositions = availableBeachPlacesPositions - 1;
                    return true;
                } else return false;
            } else throw new ReachedLimitOfObjects("The given position is already occupied by another beach place");
        } else throw new ReachedLimitOfObjects("Beach places limit reached for this sea row");
    }

    public boolean removeBeachPlace(BeachPlace beachPlace) throws IllegalStateException {
        if (!(this.BeachPlaces.contains(Objects.requireNonNull(beachPlace, "The beach place is null"))))
            throw new IllegalStateException(
                    "The beach place you are trying to remove does not exist");
        this.BeachPlaces.removeIf(b -> Objects.equals(b, beachPlace));
        this.seaRowMap.remove(beachPlace.getPosition(), beachPlace);
        return true;
    }

    

}
