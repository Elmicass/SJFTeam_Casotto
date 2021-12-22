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
	private LinkedList<Guest> participants;

	public Activity(String name, String description, Integer maxEntries) {
		this.ID = count.getAndIncrement();
		this.name = name;
		this.description = description;
		this.maxEntries = maxEntries;
		this.participants = new LinkedList<>();
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

	public LinkedList<Guest> getParticipants() {
		return participants;
	}

	@Override
	public Integer getParticipantsNumber() {
		return this.participants.size();
	}

	@Override
	public boolean addReservation(Guest guest) {
		// se il numero di partecipanti è minore di quello massimo
		// aggiungo il partecipante
		if (this.getParticipantsNumber() <= this.maxEntries) {
			this.participants.addLast(guest);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeReservation(Guest user) {
		// se l'utente è nella lista, si rimuove tale utente dalla lista.
		if (this.participants.contains(user)) {
			this.participants.remove(user);
			return true;
		}
		return false;
	}

	public String getDescription() {
		return this.description;
	}

}