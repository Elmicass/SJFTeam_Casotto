package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Users")
public class User implements UserDetails {

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
		this.ID = String.valueOf(count.incrementAndGet());
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.roles = new HashSet<>();
		this.enabled = false;
		this.locked = false;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String encodedPassword) {
		this.password = encodedPassword;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

	
}
