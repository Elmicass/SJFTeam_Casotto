package com.github.Elmicass.SFJTeam_Casotto.model;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "Privileges")
public class Privilege {

    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;

    public Privilege(String name) {
        this.ID = String.valueOf(count.getAndIncrement());
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
