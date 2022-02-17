package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
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
import javax.persistence.Transient;

import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reservation")
@NoArgsConstructor
public class Reservation implements Comparable<Reservation>, Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private BookableEntityType type;

    @ManyToOne
    @JoinColumn(name = "UserEmail", referencedColumnName = "Email")
    private User user;

    @Transient
    private IEntity entityObject;

    @ManyToOne
    @JoinColumn(name = "BeachPlaceID", referencedColumnName = "ID")
    private BeachPlace bpReference;

    @ManyToOne
    @JoinColumn(name = "ActivityID", referencedColumnName = "ID")
    private Activity actReference;

    @ManyToOne
    @JoinColumn(name = "JobOfferID", referencedColumnName = "ID")
    private JobOffer joReference;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Timeslot", referencedColumnName = "ID")
    private TimeSlot timeslot;

    @Column(name = "Price")
    private Double price;

    public Reservation(BookableEntityType type, User user, LocalDateTime start, LocalDateTime end, IEntity object)
            throws IllegalArgumentException {
        this.entityObject = object;
        setType(type);
        setEntityObject(object);
        setTimeSlot(start, end);
        setUserMail(user);
        if (this.type == BookableEntityType.BeachPlace)
            setPrice();
        else
            this.price = 0.00;
    }

    public Integer getID() {
        return id;
    }

    public BookableEntityType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public Integer getEntityID() {
        switch (type) {
            case BeachPlace:
                return bpReference.getID();
            case Activity:
                return actReference.getID();
            case JobOffer:
                return joReference.getID();
            default:
                return null;
        }
    }

    public Object getEntityObject() throws NullPointerException {
        return entityObject;
    }

    public TimeSlot getTimeSlot() {
        return timeslot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice() {
        LocalDateTime from = timeslot.getStart();
        LocalDateTime to = timeslot.getStop();
        int hours = (int) Duration.between(from, to).toHours();
        double beachPlaceHourlyPrice = bpReference.getHourlyPrice();
        double seaRowFixedCost = bpReference.getSeaRowFixedPrice();
        this.price = ((beachPlaceHourlyPrice * hours) + seaRowFixedCost);
    }

    public void setTimeSlot(LocalDateTime start, LocalDateTime end) {
        this.timeslot = Objects.requireNonNull(new TimeSlot(Objects.requireNonNull(start, "Starting time is null"),
                Objects.requireNonNull(end, "Ending time is null")), "The created timeslot is null");
    }

    public void setUserMail(User user) throws IllegalArgumentException {
        if (Objects.requireNonNull(user.getEmail(), "The referenced user email is null.").isBlank())
            throw new IllegalArgumentException("The user has no email associated.");
        this.user = user;
    }

    public void setEntityObject(IEntity entityObject) {
        Objects.requireNonNull(entityObject, "The associated object is null");
        this.entityObject = entityObject;
        BookableEntityType name = BookableEntityType.valueOf(entityObject.getClass().getSimpleName());
        switch (name) {
            case BeachPlace:
                this.bpReference = (BeachPlace) entityObject;
                break;
            case Activity:
                this.actReference = (Activity) entityObject;
                break;
            case JobOffer:
                this.joReference = (JobOffer) entityObject;
                break;
            default:
                break;
        }
    }

    public void setType(BookableEntityType type) throws IllegalArgumentException {
        switch (type) {
            case BeachPlace:
                this.type = BookableEntityType.BeachPlace;
                break;
            case Activity:
                this.type = BookableEntityType.Activity;
                break;
            case JobOffer:
                this.type = BookableEntityType.JobOffer;
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + BookableEntityType.values() + ".");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entityObject == null) ? 0 : entityObject.hashCode());
        result = prime * result + ((timeslot == null) ? 0 : timeslot.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        if (entityObject == null) {
            if (other.entityObject != null)
                return false;
        } else if (!entityObject.equals(other.entityObject))
            return false;
        if (timeslot == null) {
            if (other.timeslot != null)
                return false;
        } else if (!timeslot.equals(other.timeslot))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public int compareTo(Reservation res) {
        Objects.requireNonNull(res, "The passed reservation is null");
        if (this.timeslot.equals(res.timeslot)) {
            return this.user.getEmail().compareTo(res.user.getEmail());
        } else {
            return this.timeslot.compareTo(res.timeslot);
        }
    }

    

    




    
    













    
}
