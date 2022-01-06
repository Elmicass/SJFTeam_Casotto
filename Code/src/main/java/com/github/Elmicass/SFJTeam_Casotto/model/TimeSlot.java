package com.github.Elmicass.SFJTeam_Casotto.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Timeslot")
public class TimeSlot implements Comparable<TimeSlot> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long ID;

	// minuti massimi di tolleranza per sovrapposzione tra due timeSlot
	public static final int MINUTES_OF_TOLERANCE_FOR_OVERLAPPING = 10;

	// orario di inizio di una prenotazione
	private LocalDateTime start;

	// orario di fine di una prenotazione
	private LocalDateTime stop;

	public TimeSlot(LocalDateTime start, LocalDateTime stop) throws IllegalArgumentException {
		setStartStop(start, stop);
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getStop() {
		return stop;
	}

	public void setStartStop(LocalDateTime start, LocalDateTime stop) {
		if ((Objects.requireNonNull(start, "Starting time is null")
				.isAfter(Objects.requireNonNull(stop, "Ending time is null"))) ||
				(Objects.requireNonNull(start, "Starting time is null")
						.isEqual(Objects.requireNonNull(stop, "Ending time is null"))))
			throw new IllegalArgumentException(
					"Attempt to create a timeslot with starting time equal to, or after ending time");
		this.start = start;
		this.stop = stop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((stop == null) ? 0 : stop.hashCode());
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
		TimeSlot other = (TimeSlot) obj;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (stop == null) {
			if (other.stop != null)
				return false;
		} else if (!stop.equals(other.stop))
			return false;
		return true;
	}

	/*
	 * Un time slot precede un altro se inizia prima. Se due time slot iniziano
	 * nello stesso momento quello che finisce prima precede l'altro. Se hanno
	 * stesso inizio e stessa fine sono uguali, in compatibilità  con equals.
	 */
	@Override
	public int compareTo(TimeSlot o) throws NullPointerException {
		if (o == null)
			throw new NullPointerException("The passed object is null");
		if (this.start.equals(o.start))
			return this.stop.compareTo(o.stop);
		else
			return this.start.compareTo(o.start);
	}

	/**
	 * Determina se questo time slot si sovrappone a un altro time slot dato,
	 * considerando la soglia di tolleranza.
	 * 
	 * @param o - il time slot che viene passato per il controllo di sovrapposizione
	 * @return true se questo time slot si sovrappone per più di
	 *         MINUTES_OF_TOLERANCE_FOR_OVERLAPPING minuti a quello passato
	 * @throws NullPointerException se il time slot passato è nullo
	 */
	public boolean overlapsWith(TimeSlot o) throws NullPointerException {
		if (o == null)
			throw new NullPointerException("The passed timeslot is null");
		long begin = this.start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 60000;
		long end = this.stop.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 60000;
		long beginTimeSlot = o.start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 60000;
		long endTimeSlot = o.stop.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 60000;
		if ((endTimeSlot - beginTimeSlot) <= MINUTES_OF_TOLERANCE_FOR_OVERLAPPING
				|| (end - begin) <= MINUTES_OF_TOLERANCE_FOR_OVERLAPPING)
			return false;
		if ((end <= endTimeSlot) && (beginTimeSlot <= end))
			if (Math.abs(beginTimeSlot - end) > MINUTES_OF_TOLERANCE_FOR_OVERLAPPING)
				return true;
		if ((begin <= endTimeSlot) && (beginTimeSlot <= begin))
			if (Math.abs(begin - endTimeSlot) > MINUTES_OF_TOLERANCE_FOR_OVERLAPPING)
				return true;
		if ((beginTimeSlot <= begin) && (end <= endTimeSlot) || (begin <= beginTimeSlot) && (endTimeSlot <= end))
			return true;
		return false;
	}

}
