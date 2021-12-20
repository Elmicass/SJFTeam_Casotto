package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

import Casotto.controller.OrdersManager;

public class Customer extends Guest implements ICustomer {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final Integer ID;
	private BeachPlace beachPlace;

	public Customer(String name, String surname, String email, BeachPlace beachPlace) {
		super(name, surname, email,ActorRole.CUSTOMER);
		this.ID = count.getAndIncrement();
		this.beachPlace = beachPlace;
	}

	@Override
	public BeachPlace getBeachPlace() {
		return this.beachPlace;
	}

	@Override
	public boolean createOrder() {
		OrdersManager orderManager = new OrdersManager();
		orderManager.openNewOrder(this);
		return false;
	}
}