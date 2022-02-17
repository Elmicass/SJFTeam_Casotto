package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "Activity")
@NoArgsConstructor
public class Activity implements Comparable<Activity>, IEntity, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

	@Column(name = "Name")
	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Timeslot", referencedColumnName = "Start")
	private TimeSlot timeslot;

	@Column(name = "Description")
	private String description;

	@ManyToMany
	@JoinTable(name = "ActivitiesEquipments", joinColumns = @JoinColumn(name = "ActivityID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "EquipmentID", referencedColumnName = "ID"))
	private Set<Equipment> equipments;

	@Column(name = "MaxEntries")
	private Integer maxEntries;

	@OneToMany(mappedBy = "actReference")
	@Column(name = "Reservations")
	@OrderBy("Timeslot ASC, User_Email ASC")
	private SortedSet<Reservation> reservations;

	public Activity(String name, String description, Integer maxEntries, LocalDateTime start, LocalDateTime end,
			Set<Equipment> equipments)
			throws IllegalArgumentException {
		setName(name);
		setDescription(description);
		setMaxEntries(maxEntries);
		this.equipments = new HashSet<>();
		setEquipments(equipments);
		this.reservations = new TreeSet<Reservation>();
		setTimeSlot(start, end);
	}

	public Integer getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (Objects.requireNonNull(name, "Name value is null.").isBlank())
			throw new IllegalArgumentException("The activity name is empty.");
		this.name = name;
	}

	public TimeSlot getTimeSlot() {
		return timeslot;
	}

	public void setTimeSlot(LocalDateTime start, LocalDateTime end) {
		this.timeslot = Objects.requireNonNull(new TimeSlot(Objects.requireNonNull(start, "Starting time is null."),
				Objects.requireNonNull(end, "Ending time is null.")), "The created timeslot is null.");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws IllegalArgumentException {
		if (Objects.requireNonNull(description, "Description value is null.").isBlank())
			throw new IllegalArgumentException("The activity description is empty.");
		this.description = description;
	}

	public Integer getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(Integer maxEntries) {
		if (Objects.requireNonNull(maxEntries, "Max entries value is null.").intValue() == 0)
			this.maxEntries = Integer.MAX_VALUE;
		else 
			this.maxEntries = maxEntries;
	}

	public Set<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(Set<Equipment> equipments) {
		Objects.requireNonNull(equipments, "Equipments are null.");
		for (Equipment e : equipments)
			Objects.requireNonNull(e, "The equipments set contains a null object.");
		this.equipments = equipments;
	}

	public SortedSet<Reservation> getReservations() {
		return reservations;
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
		Objects.requireNonNull(act, "The passed activity is null.");
		if (this.timeslot.equals(act.timeslot)) {
			return this.id.compareTo(act.id);
		} else {
			return this.timeslot.compareTo(act.timeslot);
		}
	}

	public boolean addEquipment(Equipment eq) throws IllegalStateException {
		if (equipments.contains(eq))
			throw new IllegalStateException("The activity is already using this equipment.");
		return equipments.add(Objects.requireNonNull(eq, "The equipment is null."));
	}

	public boolean removeEquipment(Equipment eq) throws IllegalStateException {
		if (!(this.equipments.contains(Objects.requireNonNull(eq, "The equipment has null value."))))
			throw new IllegalStateException(
					"The equipment you are trying to remove is not being used by this activity.");
		this.equipments.removeIf(e -> Objects.equals(e, eq));
		return true;
	}

	public boolean addReservation(Reservation res) throws AlreadyExistingException, IllegalStateException {
		if (reservations.contains(res)) {
			throw new AlreadyExistingException("The user is already booked to this activity.");	}
		if (reservations.size() >= maxEntries)
			throw new IllegalStateException("The maximum number of bookings for this activity has been reached.");
		return reservations.add(Objects.requireNonNull(res, "The reservation is null."));
	}

	public boolean removeReservation(Reservation res) throws IllegalStateException {
		if (!(this.reservations.contains(Objects.requireNonNull(res, "The booking has null value."))))
			throw new IllegalStateException(
					"The booking you are trying to cancel does not exist.");
		this.reservations.removeIf(r -> Objects.equals(r, res));
		return true;
	}

}