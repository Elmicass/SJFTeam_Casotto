package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@Column(name = "Name")
	private String name;

	@Column(name = "Surname")
	private String surname;	
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Password")
	private String password;

	@Column(name = "Enabled")
	private boolean enabled;

	@Column(name = "IsTokenExpired")
	private boolean tokenExpired;

	@ManyToMany
	@JoinTable ( 
        name = "UsersRoles", 
        joinColumns = @JoinColumn(name = "UserID", referencedColumnName = "ID"), 
        inverseJoinColumns = @JoinColumn(name = "RoleID", referencedColumnName = "ID")) 
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

	public void setName(String name) throws IllegalArgumentException {
		if (Objects.requireNonNull(name, "The user name is null").isBlank())
			throw new  IllegalArgumentException("The user name is empty");
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if (Objects.requireNonNull(surname, "The user surname is null").isBlank())
			throw new  IllegalArgumentException("The user surname is empty");
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (Objects.requireNonNull(email, "The user email is null").isBlank())
			throw new  IllegalArgumentException("The user email is empty");
		this.email = email;
	}

	public Set<Role> getRole() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		Objects.requireNonNull(roles, "The user roles set is null");
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

	public boolean addRole(Role role) {
        if (roles.contains(role))
            throw new IllegalStateException("This user is already associated to the given role");
        return roles.add(Objects.requireNonNull(role, "The passed role is null"));
    }

	public boolean removeRole(Role role) {
        if (!(this.roles.contains(Objects.requireNonNull(role, "The given role is null"))))
            throw new IllegalStateException("This user is already not associated to the given role");
        this.roles.removeIf(r -> Objects.equals(r, role));
        return true;
    }
	




}
