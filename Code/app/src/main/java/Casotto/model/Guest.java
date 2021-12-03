package Casotto.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Guest implements IGuest {

	protected static final AtomicInteger count = new AtomicInteger(0);
	private final int ID;
    private String name;
    private String surname;
    private String email;
    private ActorRole role;

	public Guest() {
		this.ID = count.getAndIncrement();
		// TODO 



	
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

    public void setEmail(String email){
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


    
}
