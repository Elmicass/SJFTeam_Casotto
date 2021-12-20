package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

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
	 * Dato in input il nome il sistema setta il nome
	 * 
	 * @param name - nome
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

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
	public boolean bookActivity(String activityID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bookActivity(Activity activity) {
		// TODO Auto-generated method stub
		return false;
	}

}
