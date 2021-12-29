package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer extends Guest {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final String ID;
	private BeachPlace beachPlace;

	public Customer(String name, String surname, String email, BeachPlace beachPlace) {
		super(name, surname, email,AccountRole.CUSTOMER);
		this.ID = String.valueOf(count.getAndIncrement()); 
		this.beachPlace = beachPlace;
	}

	public BeachPlace getBeachPlace() {
		return this.beachPlace;
	}

	public boolean createOrder() {
		// TODO
		return false;
	}
}