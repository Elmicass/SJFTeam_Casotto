package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Activity implements IActivity {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final int ID;
	private String name;
	private Integer maxEntries;
	private Integer participants;

	public Activity()  {
		this.ID = count.getAndIncrement();



	
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
			this.name = name;
	}
	
	@Override
	public Integer getMaxEntries() {
		return maxEntries;
	}

	public void setMaxEntries(Integer maxEntries) {
		this.maxEntries = maxEntries;
	}

	public Integer getParticipants() {
		return participants;
	}

	@Override
	public Integer getParticipantsNumber() {
		// TODO Auto-generated method stub
		return 0;
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