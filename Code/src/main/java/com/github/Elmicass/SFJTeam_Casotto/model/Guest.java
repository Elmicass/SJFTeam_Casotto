package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Guest {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final String ID;
	private String name;
	private String surname;
	private String email;
	private AccountRole role;

	public Guest(String name, String surname, String email, AccountRole actorRole) {
		this.ID = String.valueOf(count.getAndIncrement());
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.role = actorRole;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountRole getRole() {
		return role;
	}

	public boolean bookActivity(Activity activity) {
		// TODO
		return false;
	}

}
