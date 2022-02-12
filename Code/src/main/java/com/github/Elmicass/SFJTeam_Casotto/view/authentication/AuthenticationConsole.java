package com.github.Elmicass.SFJTeam_Casotto.view.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.security.auth.login.LoginException;

import com.github.Elmicass.SFJTeam_Casotto.login.LoginService;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.authentication.registration.ConsoleRegistrationRequest;
import com.github.Elmicass.SFJTeam_Casotto.view.events.GlobalKeyListener;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConsole implements IConsoleView {

    @Autowired
    private ConsoleRegistrationRequest registrationConsole;

    @Autowired
    private LoginService loginService;

    private boolean open = true;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    public AuthenticationConsole() {
        this.commands = new HashMap<>();
    }

    public void menu() {
        clearConsoleScreen();
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Casotto Smart Chalet                                                 ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                          Sign in or login                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        System.out.println("┌                                                                      ┐");
        System.out.println("| Press [?] for:                                                       |");
        System.out.println("| [R] - Sign in                                                        |");
        System.out.println("| [L] - Login                                                          |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACKSPACE] - Go back                                                |");
        System.out.println("| [ESC] - Quit application                                             |");
        System.out.println("└                                                                      ┘");
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void open() {
        this.open = true;
        addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_R), c -> registrationConsole.registrationRequest());
        addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_L), c -> {
            try {
                loginService.login();
            } catch (LoginException le) {
                le.printStackTrace();
            }
        });
        addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_BACKSPACE), c -> close());
        menu();
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new GlobalKeyListener(commands, this));
            while(open) {
                System.out.print(" > ");
                System.out.flush();
            }
        } catch (NativeHookException nhe) {
            nhe.printStackTrace();
        }
    }

    @Override
    public void close() {
        this.open = false;
    }

    public void logoutController() {
        loginService.logout();
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
