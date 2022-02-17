package com.github.Elmicass.SFJTeam_Casotto.login;

import java.util.Objects;

public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        setEmail(email);
        setPassword(password);
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setEmail(String email) {
        if (Objects.requireNonNull(email, "The user email is null.").isBlank())
			throw new IllegalArgumentException("The user email is empty.");
        this.email = email;
    }

    public void setPassword(String password) {
        if (Objects.requireNonNull(password, "The user password is null.").isBlank())
			throw new IllegalArgumentException("The password is empty.");
        this.password = password;
    }
    
}
