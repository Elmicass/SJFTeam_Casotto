package com.github.Elmicass.SFJTeam_Casotto.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.login.LoginContextHolder;
import com.github.Elmicass.SFJTeam_Casotto.view.authentication.AuthenticationConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.events.GlobalKeyListener;
import com.github.Elmicass.SFJTeam_Casotto.view.usersViews.beachService.BeachServiceConsole;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class ConsoleView implements IConsoleView {

    @Autowired
    private AuthenticationConsole authConsole;

    @Autowired
    private BeachServiceConsole beachServiceView;

    private boolean open = true;
    
    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public ConsoleView() {
        this.commands = new HashMap<>();
        this.in = new Scanner(System.in);
    }

    
    public void start() {
        clearConsoleScreen();
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Casotto Smart Chalet                                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        System.out.println("┌                                                                      ┐");
        System.out.println("| Registration or login is required to access the application features!|");
        System.out.println("| Press " 
            + ConsoleColors.GREEN + "[SPACE]" 
                    + ConsoleColors.RESET + " to proceed or " 
                                   + ConsoleColors.RED + "[ESC]" 
                                      + ConsoleColors.RESET + " to quit the application.           |");
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
        addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_ESCAPE), c -> close());
        while(open) {
        if (LoginContextHolder.isUserLogged() == false && open == true)
            loginPhase();
        if (LoginContextHolder.isUserLogged() == true && open == true)
            mainMenuPhase();
        }

    }

    @Override
    public void close() {
        try {
            GlobalScreen.unregisterNativeHook();
            in.close();
            this.open = false;
            System.exit(0);
        } catch (NativeHookException nativeHookException) {
            nativeHookException.printStackTrace();
        }
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

    private void removeCommand(String key) {
        commands.remove(key);
    }

    public void loginPhase() {
        while (!LoginContextHolder.isUserLogged()) {
            addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_SPACE), c -> authConsole.open());
            clearConsoleScreen();
            start();
            try {
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(new GlobalKeyListener(commands, this));
            } catch (NativeHookException nhe) {
                nhe.printStackTrace();
            }
            in.nextLine();
        }
        removeCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_SPACE));
    }

    public void usersMenu() {
        clearConsoleScreen();
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Casotto Smart Chalet                                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        System.out.println("┌                                                                      ┐");
        System.out.println("| Press [?] for:                                                       |");
        System.out.println("| [S] - Servizio spiaggia                                              |");
        System.out.println("| [A] - Attività                                                       |");
        System.out.println("| [B] - Servizio bar                                                   |");
        System.out.println("| [O] - Offerte di lavoro                                              |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACKSPACE] - Go back (Logout)                                       |");
        System.out.println("| [ESC] - Quit application                                             |");
        System.out.println("└                                                                      ┘");
        System.out.flush();
    }
    
    public void mainMenuPhase() {
        switch (LoginContextHolder.getCurrentAppUser().getRoles().stream().findFirst().get().getName()) {
            case "ROLE_MANAGER":

                
                break;
            case "ROLE_EMPLOYEE":

                break;

            case "ROLE_USER":
                while (LoginContextHolder.isUserLogged()) {
                    addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_S), s -> beachServiceView.open());
                    //commands.put(NativeKeyEvent.VC_A, c -> //);
                    //commands.put(NativeKeyEvent.VC_B, c -> //);
                    //commands.put(NativeKeyEvent.VC_O, c -> //);
                    //commands.put(NativeKeyEvent.VC_BACKSPACE, c -> authConsole.logoutController());
                    addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_ESCAPE), c -> close());
                    clearConsoleScreen();
                    usersMenu();
                    try {
                        GlobalScreen.registerNativeHook();
                        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(commands, this));
                    } catch (NativeHookException nhe) {
                        nhe.printStackTrace();
                    }
                }
            default:
                break;
        }
        
    }
    


   

    

    
}
