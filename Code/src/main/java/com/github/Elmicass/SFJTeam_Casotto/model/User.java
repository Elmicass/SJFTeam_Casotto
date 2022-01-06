package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Users")
public class User {

	protected static final AtomicInteger count = new AtomicInteger(0);
	
	@Id
	@Column(name = "ID")
	private final String ID;
	
	private String name;
	private String surname;	
	
	@OneToMany(mappedBy = "userEmail")
	@Column(name = "Email")
	private String email;
	
	private String password;

	private boolean enabled;
	private boolean tokenExpired;

	@ManyToMany
	@JoinTable ( 
        name = "Users_roles", 
        joinColumns = @JoinColumn(name = "User_id", referencedColumnName = "ID"), 
        inverseJoinColumns = @JoinColumn(name = "Role_id", referencedColumnName = "ID")) 
	private Set<Role> roles;

	public User(String name, String surname, String email) {
		this.ID = String.valueOf(count.getAndIncrement());
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.roles = new HashSet<>();
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

	public Set<Role> getRole() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	




}
