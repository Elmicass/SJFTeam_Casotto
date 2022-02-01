package com.github.Elmicass.SFJTeam_Casotto.registration;

import java.util.Objects;

public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public RegistrationRequest(String firstName, String lastName, String email, String password) {
        setName(firstName);
        setSurname(lastName);
        setEmail(email);
        setPassword(password);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        if (Objects.requireNonNull(name, "The user name is null.").isBlank())
			throw new IllegalArgumentException("The user name is empty.");
        this.firstName = name;
    }

    public void setSurname(String surname) {
        if (Objects.requireNonNull(surname, "The user surname is null.").isBlank())
			throw new IllegalArgumentException("The user surname is empty.");
        this.lastName = surname;
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
