package com.github.Elmicass.SFJTeam_Casotto.login;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import com.github.Elmicass.SFJTeam_Casotto.view.login.ConsoleCallbackHandler;

public class LoginService {

    public Subject login() throws LoginException {
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

                // if we return with no exception, authentication succeeded
                break;
            } catch (LoginException le) {
                  System.err.println("Authentication failed:");
                  System.err.println("  " + le.getMessage());
                  try {
                    Thread.currentThread().sleep(3000);
                  } catch (Exception e) {
                      // ignore
                  }
            }
        }
        // did they fail three times?
        if (i == 3) {
            System.out.println("Sorry");
            System.exit(-1);
        }

        return loginContext.getSubject();
    }
    
}
