package Casotto.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Activity implements IActivity {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final int ID;
	private String name;
	private String description;
	private Integer maxEntries;
	private List<Guest> participants;

	public Activity(String aName, String aDescription, Integer aMaxEntries) {
		this.ID = count.getAndIncrement();
		this.name = aName;
		this.description = aDescription;
		this.maxEntries = aMaxEntries;
		this.participants = new LinkedList<>();
	}

	
	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
			this.name = aName;
	}
	
	@Override
	public Integer getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(Integer aMaxEntries) {
		this.maxEntries = aMaxEntries;
	}

	public List<Guest> getParticipants() {
		return participants;
	}

	@Override
	public Integer getParticipantsNumber() {
		return participants.size();
	}

	@Override
	public boolean addReservation(Guest person) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeReservation(Guest person) {
		// TODO Auto-generated method stub
		return false;
	}



}