package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

import Casotto.controller.ActivityManager;

public class Guest implements IGuest {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final int ID;
	private String name;
	private String surname;
	private String email;
	private ActorRole role;

	public Guest(String name, String surname, String email, ActorRole actorRole) {
		this.ID = count.getAndIncrement();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.role = actorRole;
	}

	/**
	 * @return - ID di Guest
	 */
	@Override
	public int getID() {
		return ID;
	}

	/**
	 * @return - il name di Guest
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Dato in input il nome, il sistema aggiorna il nome
	 * 
	 * @param name - nome di Guest
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *
	 * @ return - cognome di Guest
	 */
	@Override
	public String getSurname() {
		return surname;
	}

	/**
	 * Dato in input il cognome, il sistema aggiorna il cognome
	 *
	 * @param surname - cognome di Guest
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Restituisce l'email con cui l'ospite si è registrato.
	 * 
	 * @return - email
	 */
	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public ActorRole getRole() {
		return role;
	}

	@Override
	public boolean bookActivity(Activity activity) {

		ActivityManager activityManager = new ActivityManager();
		return activity.newBooking(this);
	}

}
