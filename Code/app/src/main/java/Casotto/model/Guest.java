package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Guest implements IGuest {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final int ID;
    private String name;
    private String surname;
    private String email;
    private ActorRole role;

	public Guest(String aName, String aSurname, String aEmail) {
		this.ID = count.getAndIncrement();
		this.name = aName;
		this.surname = aSurname;
		this.email = aEmail;
		this.role = ActorRole.GUEST;
	}
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

    public void setName(String aName){
        this.name = aName;
    }

	@Override
	public String getSurname() {
		return surname;
	}

    public void setSurname(String aSurname){
        this.surname = aSurname;
    }

	@Override
	public String getEmail() {
		return email;
	}

    public void setEmail(String aEmail){
        this.email = aEmail;
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

}
