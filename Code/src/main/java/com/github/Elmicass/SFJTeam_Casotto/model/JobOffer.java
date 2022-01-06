package com.github.Elmicass.SFJTeam_Casotto.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "JobOffer")
public class JobOffer {

    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    private String name;
    private String description;

    private TimeSlot timeslot;

    @OneToMany(mappedBy = "entityID")
    private SortedSet<Reservation> applications;

    private boolean open;

    public JobOffer(String name, String description, LocalDateTime start, LocalDateTime end) {
        this.ID = String.valueOf(count.getAndIncrement());
        setName(name);
        setDescription(description);
        setTimeSlot(start, end);
        this.open = true;
        this.applications = new TreeSet<Reservation>();
    }

    public String getID() {
        return ID;
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

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public void setTimeSlot(LocalDateTime start, LocalDateTime end) {
        this.timeslot = Objects.requireNonNull(new TimeSlot(Objects.requireNonNull(start, "Starting time is null"),
                Objects.requireNonNull(end, "Ending time is null")), "The created timeslot is null");
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

    public boolean addApplication(Reservation app) {
        if (applications.contains(app))
            throw new IllegalStateException("The user has already applied to this job offer");
        return applications.add(Objects.requireNonNull(app, "The application is null"));
    }

    public boolean removeApplication(Reservation app) throws IllegalStateException {
        if (!(this.applications.contains(Objects.requireNonNull(app, "The application has null value"))))
            throw new IllegalStateException(
                    "The application you are trying to cancel does not exist");
        this.applications.removeIf(a -> Objects.equals(a, app));
        return true;
    }

}
