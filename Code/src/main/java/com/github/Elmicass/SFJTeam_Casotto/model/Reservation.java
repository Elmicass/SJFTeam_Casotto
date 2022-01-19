package com.github.Elmicass.SFJTeam_Casotto.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Reservation")
public class Reservation implements Comparable<Reservation> {

    public enum EntityType {
        BeachPlace,
        Activity,
        JobOffer;
    }

    @Transient
    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private EntityType type;

    @ManyToOne
    @JoinColumn(name = "UserEmail", referencedColumnName = "Email")
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "EntityID", referencedColumnName = "ID")
    private String entityID;

    @Column(name = "EntityObject")
    private Optional<? extends Object> entityObject;

    @OneToOne
    @JoinColumn(name = "Timeslot", referencedColumnName = "ID")
    private TimeSlot timeslot;

    @Column(name = "Price")
    private Optional<Double> price;

    public Reservation(EntityType type, String user, String entityID, LocalDateTime start, LocalDateTime end, Object object)
            throws IllegalArgumentException {
        this.ID = String.valueOf(count.incrementAndGet());
        setEntityObject(object);
        setEntityID(entityID);
        setType(type);
        setTimeSlot(start, end);
        setUserMail(user);
        if (this.type == EntityType.BeachPlace)
            setPrice();
        else
            this.price = Optional.empty();
    }

    public String getID() {
        return ID;
    }

    public EntityType getType() {
        return type;
    }

    public String getUser() {
        return userEmail;
    }

    public String getEntityID() {
        return entityID;
    }

    public Object getEntityObject() throws NullPointerException {
        if (entityObject.isPresent())
            return entityObject.get();
        else
            throw new NullPointerException("The reservation refers to a null object");
    }

    public TimeSlot getTimeSlot() {
        return timeslot;
    }

    public double getPrice() {
        if (price.isPresent())
            return price.get().doubleValue();
        else
            return 0.00;
    }

    public void setPrice() {
        LocalDateTime from = timeslot.getStart();
        LocalDateTime to = timeslot.getStop();
        int hours = (int) Duration.between(from, to).toHours();
        BeachPlace beachPlace = (BeachPlace) entityObject.get();
        double beachPlaceHourlyPrice = beachPlace.getHourlyPrice();
        double seaRowFixedCost = beachPlace.getSeaRowFixedPrice();
        this.price = Optional.of((beachPlaceHourlyPrice * hours) + seaRowFixedCost);
    }

    public void setTimeSlot(LocalDateTime start, LocalDateTime end) {
        this.timeslot = Objects.requireNonNull(new TimeSlot(Objects.requireNonNull(start, "Starting time is null"),
                Objects.requireNonNull(end, "Ending time is null")), "The created timeslot is null");
    }

    public void setUserMail(String email) throws IllegalArgumentException {
        if (Objects.requireNonNull(email, "The user email value is null").isBlank())
            throw new IllegalArgumentException("The user has no email associated");
        this.userEmail = email;
    }

    public void setEntityID(String id) throws IllegalArgumentException {
        if (Objects.requireNonNull(id, "The entity id value is null").isBlank())
            throw new IllegalArgumentException("The entity ID is empty");
        this.entityID = id;
    }

    public void setEntityObject(Object entityObject) {
        Objects.requireNonNull(entityObject, "The associated object is null");
        if (this.entityObject.isEmpty())
            this.entityObject = Optional.of(entityObject);
    }

    public void setType(EntityType type) throws IllegalArgumentException {
        switch (type) {
            case BeachPlace:
                this.type = EntityType.BeachPlace;
                break;
            case Activity:
                this.type = EntityType.Activity;
                break;
            case JobOffer:
                this.type = EntityType.JobOffer;
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + EntityType.values() + ".");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entityID == null) ? 0 : entityID.hashCode());
        result = prime * result + ((timeslot == null) ? 0 : timeslot.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
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
        Reservation other = (Reservation) obj;
        if (entityID == null) {
            if (other.entityID != null)
                return false;
        } else if (!entityID.equals(other.entityID))
            return false;
        if (timeslot == null) {
            if (other.timeslot != null)
                return false;
        } else if (!timeslot.equals(other.timeslot))
            return false;
        if (type != other.type)
            return false;
        if (userEmail == null) {
            if (other.userEmail != null)
                return false;
        } else if (!userEmail.equals(other.userEmail))
            return false;
        return true;
    }

    @Override
    public int compareTo(Reservation res) {
        Objects.requireNonNull(res, "The passed reservation is null");
        if (this.timeslot.equals(res.timeslot)) {
            return this.entityID.compareTo(res.entityID);
        } else {
            return this.timeslot.compareTo(res.timeslot);
        }
    }

    

    




    
    













    
}
