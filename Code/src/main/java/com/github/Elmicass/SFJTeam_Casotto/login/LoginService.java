package com.github.Elmicass.SFJTeam_Casotto.login;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import com.github.Elmicass.SFJTeam_Casotto.email.EmailService;
import com.github.Elmicass.SFJTeam_Casotto.registration.EmailValidatorService;
import com.github.Elmicass.SFJTeam_Casotto.security.PasswordEncoder;
import com.github.Elmicass.SFJTeam_Casotto.services.ConfirmationTokenServices;
import com.github.Elmicass.SFJTeam_Casotto.services.IUserServices;
import com.github.Elmicass.SFJTeam_Casotto.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private IUserServices userServices;

    @Autowired
    private EmailValidatorService emailValidator;

    @Autowired
    private ConfirmationTokenServices tokensServices;

    @Autowired
    private EmailService emailSender;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;
    
    // username and password
    private String username;
    private String password;

    private Subject subject;
    private Principal userPrincipal;

    public boolean login(LoginRequest userData) throws LoginException {
        boolean isValidEmail = emailValidator.test(userData.getEmail());
        if (!isValidEmail)
            throw new IllegalArgumentException("The email entered is not valid.");
        username = userData.getEmail();
        password = userData.getPassword();
        boolean usernameCorrect = false;
        boolean passwordCorrect = false;
        if (userServices.loadUserByUsername(username) != null) {
            if (username.equals(userServices.loadUserByUsername(userData.getEmail()).getUsername())) {
                usernameCorrect = true;
            }
            if (usernameCorrect && (passwordEncoder.bCryptPasswordEncoder().matches(password, userServices.loadUserByUsername(username).getPassword()))) {
                passwordCorrect = true;
                if (!(userServices.loadUserByUsername(username).isEnabled())) {
                    System.out.println("\t\t[LoginModule] " + "authentication failed.");
                    emailSender.send(username, emailSender.getEmailBody(userServices.getUserByUsername(username).getName(), tokensServices.generateFullTokenLink()));
                    succeeded = false;
                    username = null;
                    password = null;
                    throw new FailedLoginException(
                        "The email has not yet been verified. A new verification link has been sent to the corresponding email. Make sure to confirm your email within 20 mins or the link will expire.");
                } else {
                    System.out.println("\t\t[LoginModule] " + "authentication succeeded");
                    succeeded = true;
                }
            } else {
                System.out.println("\t\t[LoginModule] " + "authentication failed.");
                succeeded = false;
                username = null;
                password = null;
                throw new FailedLoginException("Password incorrect!");
            }
        } else {
            String userNotFound = String.format(UserServices.USER_NOT_FOUND_MSG, username);
            System.out.println("\t\t[LoginModule] " + "authentication failed.");
            succeeded = false;
            username = null;
            password = null;
            throw new FailedLoginException("Username incorrect! " + userNotFound);
        }
        if (succeeded == true) {
            commit();
            if (commitSucceeded == false) {
                abort();
            }
        } else {
            abort();
        }
        return (succeeded && commitSucceeded);
    }

    public boolean commit() throws LoginException {
        if (succeeded == false) {
            return false;
        } else {
            // add a Principal (authenticated identity) to the Subject
            // assume the user we authenticated is the UserPrincipal
            userPrincipal = new UserPrincipal(userServices.getUserByUsername(username));
            subject = new Subject();
            if (!subject.getPrincipals().contains(userPrincipal))
                subject.getPrincipals().add(userPrincipal);
            if (LoginContextHolder.isUserLogged() == false) {
                LoginContextHolder.setUserLogged(true);
                LoginContextHolder.setCurrentAppUser(userServices.loadUserByUsername(username));
            }
            // in any case, clean out state
            username = null;
            password = null;
            commitSucceeded = true;
            return true;
        }
    }

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

    public void logout() {
        if (LoginContextHolder.isUserLogged()) {
            LoginContextHolder.clear();
            LoginContextHolder.setUserLogged(false);
        }
        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        succeeded = commitSucceeded;
        username = null;
        if (password != null)
            password = null;
        userPrincipal = null;
    }
    

}


    

