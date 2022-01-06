package com.github.Elmicass.SFJTeam_Casotto.model;

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
@Table(name = "Roles")
public class Role {

    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
	private final String ID;

    private String name;
    
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "Role_id", referencedColumnName = "ID"), 
        inverseJoinColumns = @JoinColumn(
          name = "Privilege_id", referencedColumnName = "ID"))
    private Set<Privilege> privileges;

    public Role(String name) {
        this.ID = String.valueOf(count.getAndIncrement());
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public Set<Privilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
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
        Role other = (Role) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    
    
}
