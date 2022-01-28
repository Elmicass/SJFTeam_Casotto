package com.github.Elmicass.SFJTeam_Casotto.login;


import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.github.Elmicass.SFJTeam_Casotto.security.PasswordEncoder;
import com.github.Elmicass.SFJTeam_Casotto.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;

public class LoginModuleImplementation implements LoginModule {

    @Autowired
    private UserServices usersServices;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    // configurable option
    private boolean debug = false;

    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;
    
    // username and password
    private String username;
    private String password;

    private Principal userPrincipal;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Username: ");
        callbacks[1] = new PasswordCallback("Password: ", false);
        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback) callbacks[0]).getName();
            String tmpPassword = new String(((PasswordCallback) callbacks[1]).getPassword());
            password = tmpPassword;
        } catch (IOException ioe) {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException uce) {
            throw new LoginException("Error: " + uce.getCallback().toString() +
                    " not available to garner authentication information " +
                    "from the user");
        }
        boolean usernameCorrect = false;
        boolean passwordCorrect = false;
        if (usersServices.exists(username)) {
            if (username.equals(usersServices.loadUserByUsername(username).getUsername())) {
                usernameCorrect = true;
            }
            if (usernameCorrect && (passwordEncoder.bCryptPasswordEncoder().matches(password,
                    usersServices.loadUserByUsername(username).getPassword()))) {
                passwordCorrect = true;
                if (debug)
                    System.out.println("\t\t[LoginModule] " + "authentication succeeded");
                succeeded = true;
                return true;
            } else {
                if (debug)
                    System.out.println("\t\t[LoginModule] " + "authentication failed.");
                succeeded = false;
                username = null;
                password = null;
                throw new FailedLoginException("Password incorrect!");
            }
        } else {
            String userNotFound = String.format(UserServices.USER_NOT_FOUND_MSG, username);
            if (debug)
                System.out.println("\t\t[LoginModule] " + "authentication failed.");
            succeeded = false;
            username = null;
            password = null;
            throw new FailedLoginException("Username incorrect! " + userNotFound);
        }
    }

    @Override
    public boolean commit() throws LoginException {
        if (succeeded == false) {
            return false;
        } else {
            // add a Principal (authenticated identity) to the Subject
            // assume the user we authenticated is the UserPrincipal
            userPrincipal = new UserPrincipal(usersServices.loadUserByUsername(username));
            if (!subject.getPrincipals().contains(userPrincipal))
                subject.getPrincipals().add(userPrincipal);
            // in any case, clean out state
            username = null;
            password = null;
            commitSucceeded = true;
            return true;
        }
    }

    @Override
    public boolean abort() throws LoginException {
        if (succeeded == false) {
            return false;
        } else if (succeeded == true && commitSucceeded == false) {
            // login succeeded but overall authentication failed
            succeeded = false;
            username = null;
            if (password != null)
                password = null;
            userPrincipal = null;
        } else {
            // overall authentication succeeded and commit succeeded,
            // but someone else's commit failed
            logout();
        }
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        succeeded = commitSucceeded;
        username = null;
        if (password != null)
            password = null;
        userPrincipal = null;
        return true;
    }
    
}
