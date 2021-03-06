package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SortNatural;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "JobOffer")
@NoArgsConstructor
public class JobOffer implements Comparable<JobOffer>, IEntity, Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Timeslot", referencedColumnName = "ID")
    private TimeSlot timeslot;

    @OneToMany(mappedBy = "joReference")
    @Column(name = "Applications")
    @SortNatural
    private SortedSet<Reservation> applications;

    @Column(name = "IsOpen")
    private boolean open;

    public JobOffer(String name, String description, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException {
        setName(name);
        setDescription(description);
        settimeslot(start, end);
        this.open = true;
        this.applications = new TreeSet<Reservation>();
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.requireNonNull(name, "Name value is null").isBlank())
            throw new IllegalArgumentException("The job offer name is empty");
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (Objects.requireNonNull(description, "Description value is null").isBlank())
            throw new IllegalArgumentException("The job offer description is empty");
        this.description = description;
    }

    public TimeSlot gettimeslot() {
        return timeslot;
    }

    public void settimeslot(LocalDateTime start, LocalDateTime end) {
        this.timeslot = Objects.requireNonNull(new TimeSlot(Objects.requireNonNull(start, "Starting time is null"),
                Objects.requireNonNull(end, "Ending time is null"), this), "The created timeslot is null");
    }

    public SortedSet<Reservation> getApplications() {
        return applications;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public int compareTo(JobOffer jo) {
        Objects.requireNonNull(jo,"The passed activity is null");
        if (this.timeslot.equals(jo.timeslot)) {
            return this.id.compareTo(jo.id);
        } else {
            return this.timeslot.compareTo(jo.timeslot);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((timeslot == null) ? 0 : timeslot.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        JobOffer other = (JobOffer) obj;
        if (timeslot == null) {
            if (other.timeslot != null)
                return false;
        } else if (!timeslot.equals(other.timeslot))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public boolean addReservation(Reservation app) {
        if (!(applications.isEmpty())) {
			if (applications.contains(app))
            throw new IllegalStateException("The user has already applied to this job offer");
		}
        return applications.add(Objects.requireNonNull(app, "The application is null"));
    }

    @Override
    public boolean removeReservation(Reservation app) throws IllegalStateException {
        if (!(this.applications.contains(Objects.requireNonNull(app, "The application has null value"))))
            throw new IllegalStateException(
                    "The application you are trying to cancel does not exist for this job offer");
        this.applications.removeIf(a -> Objects.equals(a, app));
        return true;
    }

}
