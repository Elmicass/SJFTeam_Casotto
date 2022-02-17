package com.github.Elmicass.SFJTeam_Casotto.view.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.authentication.login.ConsoleLoginRequest;
import com.github.Elmicass.SFJTeam_Casotto.view.authentication.registration.ConsoleRegistrationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConsole implements IConsoleView {

    @Autowired
    private ConsoleRegistrationRequest registrationConsole;

    @Autowired
    private ConsoleLoginRequest loginConsole;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner authIn;

    public AuthenticationConsole() {
        this.commands = new HashMap<>();
        this.open = true;
        this.authIn = new Scanner(System.in);
    }

    public void menu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                          Sign in or login                            |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [R] - Sign in                                                        |");
        System.out.println("| [L] - Login                                                          |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back                                                     |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void open() {
        commands.clear();
        this.open = true;
        while(open) {
            addCommand("R", c -> registrationConsole.registrationRequest());
            addCommand("L", c -> { if (loginConsole.loginRequest()) close(); });
            addCommand("BACK", c -> close());
            addCommand("0", c -> System.exit(0));
            clearConsoleScreen();
            menu();
            System.out.print(" > ");
            System.out.flush();
            String command = authIn.nextLine();
            processCommand(command);
        }
    }

    @Override
    public void close() {
        this.open = false;
        commands.clear();       
    }

    public void logoutController() {
        loginConsole.logoutRequest();
    }

    @Override
    public void processCommand(String commandName) {
        try {
            Consumer<? super IConsoleView> command = this.commands.get(commandName);
            if (command == null) {
                System.err.println("Unknown command: " + commandName);
            } else
                command.accept(this);
        } catch (NumberFormatException e) {
            System.err.println("Unknown command: " + commandName);
        }
    }

    private void addCommand(String key, Consumer<? super IConsoleView> command) {
        commands.put(key, command);
    }



    
}
