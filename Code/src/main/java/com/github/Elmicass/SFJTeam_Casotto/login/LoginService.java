package com.github.Elmicass.SFJTeam_Casotto.login;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.services.UserServices;
import com.github.Elmicass.SFJTeam_Casotto.view.authentication.login.ConsoleCallbackHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UserServices userServices;

    public User login() throws LoginException {
        LoginContext loginContext = null;
        try {
            loginContext = new LoginContext("jaasApplication", new ConsoleCallbackHandler());
        } catch (LoginException le) {
            System.err.println("Cannot create LoginContext. " + le.getMessage());
            System.exit(-1);
        } catch (SecurityException se) {
            System.err.println("Cannot create LoginContext. " + se.getMessage());
            System.exit(-1);
        }
        int i;
        for (i = 0; i < 3; i++) {
            try {
                // attempt authentication
                loginContext.login();
                LoginContextHolder.setCurrentLoginContext(loginContext);
                // if we return with no exception, authentication succeeded
                break;
            } catch (LoginException le) {
                  System.err.println("Authentication failed:");
                  System.err.println("  " + le.getMessage());
                  try {
                    Thread.sleep(3000);
                  } catch (Exception e) {
                      // ignore
                  }
            }
        }
        // did they fail three times?
        if (i == 3) {
            System.out.println("Sorry, you have finished the available login attempts. If you want to try again, please restart the application.");
            System.exit(-1);
        }
        if (LoginContextHolder.isUserLogged() == false) {
            LoginContextHolder.setUserLogged(true);
            LoginContextHolder.setCurrentAppUser(userServices.loadUserByUsername(loginContext.getSubject().getPrincipals().stream().findFirst().get().getName()));
        }
        return LoginContextHolder.getCurrentAppUser();
    }

    public void logout() {
        try {
            if (LoginContextHolder.isUserLogged()) {
                LoginContextHolder.getCurrentLoginContext().logout();
                LoginContextHolder.clear();
                LoginContextHolder.setUserLogged(false);
            }
        } catch (LoginException le) {
            System.err.println("Logout failed:");
            System.err.println("  " + le.getMessage());
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                // ignore
            }
        }
    }

}


    

