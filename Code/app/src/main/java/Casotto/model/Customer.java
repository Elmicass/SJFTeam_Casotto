package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer extends Guest implements ICustomer  {

    protected static final AtomicInteger count = new AtomicInteger(0);
	private final int ID;
    private String name;
    private String surname;
    private String email;
	private BeachPlace beachPlace;

	public Customer() {
		this.ID = count.getAndIncrement();



	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

    public void setName(String name){
        this.name = name;
    }

	@Override
	public String getSurname() {
		return surname;
	}

    public void setSurname(String surname){
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
