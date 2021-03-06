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

@Entity
@Table(name = "Privileges")
@NoArgsConstructor
public class Privilege implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;

    @Column(name = "Name")
    private String name;

    @ManyToMany(mappedBy = "privileges")
    @Column(name = "Roles")
    private Set<Role> roles;

    public Privilege(String name) throws IllegalArgumentException {
        setName(name);
        this.roles = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.requireNonNull(name, "The privilege name value is null").isBlank())
            throw new IllegalArgumentException("The privilege name value is empty");
        this.name = name;
    }

    public void setRoles(Set<Role> roles) {
        Objects.requireNonNull(roles, "The passed roles set is null");
        this.roles = roles;
    }

    public boolean addRole(Role role) {
        if (roles.contains(role))
            throw new IllegalStateException("This privilege is already associated to the given role");
        return roles.add(Objects.requireNonNull(role, "The passed role is null"));
    }

    public boolean removeRole(Role role) {
        if (!(this.roles.contains(Objects.requireNonNull(role, "The given role is null"))))
            throw new IllegalStateException("This privilege is already not associated to the given role");
        this.roles.removeIf(r -> Objects.equals(r, role));
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Privilege other = (Privilege) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
