package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer extends Guest implements ICustomer {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final Integer ID;
	private BeachPlace beachPlace;

	public Customer(String aName, String aSurname, String aEmail, BeachPlace beachPlace) {
		super(aName, aSurname, aEmail);
		this.ID = count.getAndIncrement();
		this.beachPlace = beachPlace;
	}

	@Override
	public boolean bookActivity(String activityID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BeachPlace getBeachPlace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createOrder() {
		// TODO Auto-generated method stub
		return false;
	}
}
