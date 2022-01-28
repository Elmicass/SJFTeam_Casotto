package com.github.Elmicass.SFJTeam_Casotto.registration;

public class RegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    
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
        this.firstName = name;
    }

    public void setSurname(String surname) {
        this.lastName = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
