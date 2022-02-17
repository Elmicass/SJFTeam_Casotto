package com.github.Elmicass.SFJTeam_Casotto.login;

import javax.annotation.PostConstruct;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginContextHolder {

    private static boolean userLogged = false;

    private static ThreadLocal<UserDetails> currentAppUser;

    @PostConstruct
    private void initialize() {
        currentAppUser = new ThreadLocal<>();
    }

    public static boolean isUserLogged() {
        return userLogged;
    }

    public static void setUserLogged(boolean status) {
        userLogged = status;
    }

    public static UserDetails getCurrentAppUser() {
        return currentAppUser.get();
    }

    public static void setCurrentAppUser(UserDetails user) {
        currentAppUser.set(user);
    }

    public static void clear() {
        currentAppUser.remove();
    }
    
}
