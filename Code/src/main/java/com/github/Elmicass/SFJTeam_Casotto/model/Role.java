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
import javax.persistence.Transient;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Roles")
public class Role {

    @Transient
    protected static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private final String ID;

    @Column(name = "Name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Column(name = "Users")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "RolesPrivileges", joinColumns = @JoinColumn(name = "RoleID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PrivilegeID", referencedColumnName = "ID"))
    @Column(name = "Privileges")
    private Set<Privilege> privileges;

    public Role(String name) {
        this.ID = String.valueOf(count.incrementAndGet());
        this.name = name;
        this.users = new HashSet<>();
        this.privileges = new HashSet<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (Objects.requireNonNull(name, "The role name is null").isBlank())
            throw new IllegalArgumentException("The role name is empty");
        this.name = name;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        Objects.requireNonNull(users, "The role users set is null");
        this.users = users;
    }

    public Set<Privilege> getPrivileges() {
        return this.privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        Objects.requireNonNull(privileges, "The role privileges set is null");
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

    public boolean addUser(User user) {
        if (users.contains(user))
            throw new IllegalStateException("This role is already associated to the given user");
        return users.add(Objects.requireNonNull(user, "The passed user is null"));
    }

    public boolean removeUser(User user) {
        if (!(this.users.contains(Objects.requireNonNull(user, "The given user is null"))))
        throw new IllegalStateException("This role is already not associated to the given user");
        this.users.removeIf(u -> Objects.equals(u, user));
        return true;
    }

    public boolean addPrivilege(Privilege privilege) {
        if (privileges.contains(privilege))
            throw new IllegalStateException("This role is already associated to the given privilege");
        return privileges.add(Objects.requireNonNull(privilege, "The passed privilege is null"));
    }

    public boolean removePrivilege(Privilege privilege) {
        if (!(this.privileges.contains(Objects.requireNonNull(privilege, "The given privilege is null"))))
        throw new IllegalStateException("This role is already not associated to the given privilege");
        this.privileges.removeIf(p -> Objects.equals(p, privilege));
        return true;
    }








}
