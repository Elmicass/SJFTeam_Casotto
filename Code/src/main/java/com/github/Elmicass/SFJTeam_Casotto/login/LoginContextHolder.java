package com.github.Elmicass.SFJTeam_Casotto.login;

import javax.security.auth.login.LoginContext;

import com.github.Elmicass.SFJTeam_Casotto.model.User;

import org.springframework.stereotype.Component;

@Component
public class LoginContextHolder {

    private static boolean userLogged = false;

    private static LoginContext currentLoginContext;

    private static ThreadLocal<User> currentAppUser;

    public static boolean isUserLogged() {
        return userLogged;
    }

    public static void setUserLogged(boolean status) {
        userLogged = status;
    }

    public static LoginContext getCurrentLoginContext() {
        return currentLoginContext;
    }

    public static void setCurrentLoginContext(LoginContext loginContext) {
        currentLoginContext = loginContext;
    }

    public static User getCurrentAppUser() {
        return currentAppUser.get();
    }

    public static void setCurrentAppUser(User user) {
        currentAppUser.set(user);
    }

    public static void clear() {
        currentAppUser.remove();
        currentLoginContext = null;
    }
    
}
