package com.github.Elmicass.SFJTeam_Casotto.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Reservation")
public class Reservation implements Comparable<Reservation> {

    public enum entityType {
        BeachPlace,
        Activity,
        JobOffer;
    }

    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    @Enumerated(EnumType.STRING)
    private entityType type;

    @ManyToOne
    @JoinColumn(name = "User_email", referencedColumnName = "Email")
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "Entity_ID", referencedColumnName = "ID")
    private String entityID;

    private TimeSlot timeslot;

    public Reservation(String type, String user, String entityID, LocalDateTime start, LocalDateTime end)
            throws IllegalArgumentException {
        this.ID = String.valueOf(count.getAndIncrement());
        setType(type);
        setUserMail(user);
        setEntityID(entityID);
        setTimeSlot(start, end);
    }

    public String getID() {
        return ID;
    }

    public entityType getType() {
        return type;
    }

    public String getUser() {
        return userEmail;
    }

    public String getEntityID() {
        return entityID;
    }

    public TimeSlot getTimeSlot() {
        return timeslot;
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
		this.entityID =  id;
    }

    public void setType(String typeStringName) throws IllegalArgumentException {
        entityType type = entityType.valueOf(typeStringName);
        switch (type) {
            case BeachPlace:
                this.type = entityType.BeachPlace;
                break;
            case Activity:
                this.type = entityType.Activity;
                break;
            case JobOffer:
                this.type = entityType.JobOffer;
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + entityType.values() + ".");
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
        Objects.requireNonNull(res,"The passed reservation is null");
        if (this.timeslot.equals(res.timeslot)) {
            return this.entityID.compareTo(res.entityID);
        } else {
            return this.timeslot.compareTo(res.timeslot);
        }
    }

    

    




    
    













    
}
