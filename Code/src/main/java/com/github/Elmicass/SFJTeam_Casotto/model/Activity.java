package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class Activity {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final String ID;
	private String name;
	private String description;
	private Integer maxEntries;
	private LinkedList<Guest> participants;

	public Activity(String name, String description, Integer maxEntries) {
		this.ID = String.valueOf(count.getAndIncrement());
		this.name = name;
		this.description = description;
		this.maxEntries = maxEntries;
		this.participants = new LinkedList<>();
	}

	
	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(Integer maxEntries) {
		this.maxEntries = maxEntries;
	}

	public LinkedList<Guest> getParticipants() {
		return participants;
	}

	public Integer getParticipantsNumber() {
		return participants.size();
	}

	public boolean addReservation(Guest guest) {
		return participants.add(guest);
	}

	public boolean removeReservation(Guest guest) {
		return participants.remove(guest);
	}



}