package com.github.Elmicass.SFJTeam_Casotto.view.authentication.login;

import java.io.Console;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.github.Elmicass.SFJTeam_Casotto.login.LoginController;
import com.github.Elmicass.SFJTeam_Casotto.login.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsoleLoginRequest {

    @Autowired
    private LoginController controller;

    public boolean loginRequest() {
        Console console = System.console();
        Scanner scanner;
        int i;
        for (i = 0; i < 3; i++) {
            if (console != null) {
                try {
                    String email = console.readLine("Enter your email: ");
                    String password = new String(console.readPassword("Enter your password: "));
                    LoginRequest request = new LoginRequest(email, password);
                    try {
                        if (controller.login(request))
                            return true;
                    } catch (LoginException le) {
                        System.err.println("Login failed:");
                        System.err.println("  " + le.getMessage());
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                              // ignore
                        }
                        return false;
                    }
                } catch (IllegalArgumentException ie) {
                    System.err.println("Login failed:");
                    System.err.println("  " + ie.getMessage());
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                          // ignore
                    }
                    return false;
                }
            } else {
                try {
                    scanner = new Scanner(System.in);
                    System.out.println("Enter your email: ");
                    String email = scanner.nextLine();
                    System.out.println("Enter your password: ");
                    String password = scanner.nextLine();
                    LoginRequest userData = new LoginRequest(email, password);
                    try {
                        if (controller.login(userData))
                            return true;
                    } catch (LoginException le) {
                        System.err.println("Login failed:");
                        System.err.println("  " + le.getMessage());
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                              // ignore
                        }
                        return false;
                    }
                } catch (IllegalArgumentException ie) {
                    System.err.println("Login failed:");
                    System.err.println("  " + ie.getMessage());
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                          // ignore
                    }
                    return false;
                }
            }
        }
        // did they fail three times?
        if (i == 3) {
            System.out.println("Sorry, you have finished the available registration attempts. If you want to try again, please restart the application.");
            System.exit(-1);
        }
        return false;
    }

    public void logoutRequest() {
        controller.logout();
    }




    
}
