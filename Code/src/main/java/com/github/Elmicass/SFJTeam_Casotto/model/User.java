package com.github.Elmicass.SFJTeam_Casotto.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "AppUser")
@NoArgsConstructor
public class User implements Comparable<User>, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "Name")
	private String name;

	@Column(name = "Surname")
	private String surname;	
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Password")
	private String password;

	@Column(name = "Locked")
	private boolean locked;

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

	public User(String name, String surname, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.roles = new HashSet<>();
		this.enabled = false;
		this.locked = false;
	}

	public Integer getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (Objects.requireNonNull(name, "The user name is null").isBlank())
			throw new IllegalArgumentException("The user name is empty");
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		if (Objects.requireNonNull(surname, "The user surname is null").isBlank())
			throw new IllegalArgumentException("The user surname is empty");
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (Objects.requireNonNull(email, "The user email is null").isBlank())
			throw new IllegalArgumentException("The user email is empty");
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		Objects.requireNonNull(roles, "The user roles set is null");
		this.roles = roles;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	
	public int compareTo(User user) {
        Objects.requireNonNull(user, "The passed user is null");
        if (this.name.equals(user.name)) {
            return this.surname.compareTo(user.surname);
        } else {
            return this.name.compareTo(user.name);
        }
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String encodedPassword) {
		if (Objects.requireNonNull(encodedPassword, "The user password is null").isBlank())
			throw new IllegalArgumentException("The password is empty");
		this.password = encodedPassword;
	}

	public String getUsername() {
		return email;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return !locked;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

    

}
