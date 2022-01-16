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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Activity")
public class Activity implements Comparable<Activity> {

	@Transient
	protected static final AtomicInteger count = new AtomicInteger(0);

	@Id
	@Column(name = "ID")
	private final String ID;

	@Column(name = "Name")
	private String name;

	@OneToOne
	@JoinColumn(name = "Timeslot", referencedColumnName = "Start")
	private TimeSlot timeslot;

	@Column(name = "Description")
	private String description;

	@ManyToMany
	@JoinTable(name = "ActivitiesEquipments", joinColumns = @JoinColumn(name = "ActivityID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "EquipmentID", referencedColumnName = "ID"))
	private Set<Equipment> equipments;

	@Column(name = "MaxEntries")
	private Integer maxEntries;

	@OneToMany(mappedBy = "entityID")
	@Column(name = "Reservations")
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

	public SortedSet<Reservation> getReservations() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((timeslot == null) ? 0 : timeslot.hashCode());
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
		Activity other = (Activity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timeslot == null) {
			if (other.timeslot != null)
				return false;
		} else if (!timeslot.equals(other.timeslot))
			return false;
		return true;
	}

	@Override
	public int compareTo(Activity act) {
		Objects.requireNonNull(act, "The passed activity is null");
		if (this.timeslot.equals(act.timeslot)) {
			return this.ID.compareTo(act.ID);
		} else {
			return this.timeslot.compareTo(act.timeslot);
		}
	}

	public boolean addEquipment(Equipment eq) {
		if (equipments.contains(eq))
			throw new IllegalStateException("The activity is already using this equipment");
		return equipments.add(Objects.requireNonNull(eq, "The equipment is null"));
	}

	public boolean removeEquipment(Equipment eq) throws IllegalStateException {
		if (!(this.equipments.contains(Objects.requireNonNull(eq, "The equipment has null value"))))
			throw new IllegalStateException(
					"The equipment you are trying to remove is not being used by this activity");
		this.equipments.removeIf(e -> Objects.equals(e, eq));
		return true;
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