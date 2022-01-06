package com.github.Elmicass.SFJTeam_Casotto.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Activity")
public class Activity {

	protected static final AtomicInteger count = new AtomicInteger(0);

	@Id
	@Column(name = "ID")
	private final String ID;

	private String name;
	private TimeSlot timeslot;
	private String description;

	@OneToMany
	private Set<Equipment> equipments;
	private Integer maxEntries;

	@OneToMany(mappedBy = "entityID")
	private SortedSet<Reservation> reservations;

	public Activity(String name, String description, Integer maxEntries, LocalDateTime start, LocalDateTime end,
			Set<Equipment> equipments)
			throws IllegalArgumentException, NullPointerException {
		this.ID = String.valueOf(count.getAndIncrement());
		setName(name);
		setDescription(description);
		setMaxEntries(maxEntries);
		setEquipments(equipments);
		this.reservations = new TreeSet<Reservation>();
		setTimeSlot(start, end);
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public TimeSlot getTimeSlot() {
		return timeslot;
	}

	public String getDescription() {
		return description;
	}

	public Integer getMaxEntries() {
		return maxEntries;
	}

	public Set<Equipment> getEquipments() {
		return equipments;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (Objects.requireNonNull(name, "Name value is null").isBlank())
			throw new IllegalArgumentException("The activity name is empty");
		this.name = name;
	}

	public void setDescription(String description) throws IllegalArgumentException {
		if (Objects.requireNonNull(description, "Description value is null").isBlank())
			throw new IllegalArgumentException("The activity description is empty");
		this.description = description;
	}

	public void setEquipments(Set<Equipment> equipments) throws IllegalArgumentException {
		if (Objects.requireNonNull(equipments, "Equipments are null").isEmpty())
			throw new IllegalArgumentException("The equipments set is empty");
		for (Equipment e : equipments)
			Objects.requireNonNull(e, "The equipments set contains a null object");
		this.equipments = equipments;
	}

	public void setTimeSlot(LocalDateTime start, LocalDateTime end) {
		this.timeslot = Objects.requireNonNull(new TimeSlot(Objects.requireNonNull(start, "Starting time is null"),
				Objects.requireNonNull(end, "Ending time is null")), "The created timeslot is null");
	}

	public void setMaxEntries(int maxEntries) {
		if (Objects.requireNonNull(maxEntries, "Max entries value is null").intValue() == 0)
			this.maxEntries = Integer.MAX_VALUE;
		this.maxEntries = Objects.requireNonNull(maxEntries, "Max entries value is null");
	}

	public boolean addEquipment(Equipment eq) {
		if (equipments.contains(eq))
			throw new IllegalStateException("The activity is already using this equipment");
		return equipments.add(Objects.requireNonNull(eq, "The equipment is null"));
	}

	public boolean addReservation(Reservation res) {
		if (reservations.contains(res))
			throw new IllegalStateException("The user is already booked to this activity");
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