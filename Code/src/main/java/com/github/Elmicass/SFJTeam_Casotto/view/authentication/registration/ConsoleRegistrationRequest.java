package com.github.Elmicass.SFJTeam_Casotto.view.authentication.registration;

import java.io.Console;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.registration.RegistrationController;
import com.github.Elmicass.SFJTeam_Casotto.registration.RegistrationRequest;

import org.springframework.beans.factory.annotation.Autowired;

public class ConsoleRegistrationRequest {

    @Autowired
    RegistrationController controller;

    public void registrationRequest() {
        Console console = System.console();
        int i;
        for (i = 0; i < 3; i++) {
            try {
                String firstName = console.readLine("Enter your first name: ");
                String lastName = console.readLine("Enter your last name: ");
                String email = console.readLine("Enter your email: ");
                String password = new String(console.readPassword("Enter your password: "));
                RegistrationRequest userData = new RegistrationRequest(firstName, lastName, email, password);
                try {
                    if (controller.register(userData))
                        break;
                } catch (AlreadyExistingException ae) {
                    System.err.println("Registration failed:");
                    System.err.println("  " + ae.getMessage());
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (Exception e) {
                          // ignore
                    }
                }
            } catch (IllegalArgumentException ie) {
                System.err.println("Registration failed:");
                System.err.println("  " + ie.getMessage());
                try {
                    Thread.currentThread().sleep(3000);
                } catch (Exception e) {
                      // ignore
                }
            }
        }
        // did they fail three times?
        if (i == 3) {
            System.out.println("Sorry, you have finished the available registration attempts. If you want to try again, please restart the application.");
            System.exit(-1);
        }  
    }

}