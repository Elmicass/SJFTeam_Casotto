package com.github.Elmicass.SFJTeam_Casotto.login;

import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;

import com.github.Elmicass.SFJTeam_Casotto.model.User;

public class UserPrincipal implements Principal, Serializable {

    /**
     * @serial
     */
    private User user;

    public UserPrincipal(User userDetails) {
        setUser(userDetails);
    }

    private void setUser(User user) {
        Objects.requireNonNull(user, "The logged user is null.");
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        UserPrincipal other = (UserPrincipal) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    

}
