package com.github.Elmicass.SFJTeam_Casotto.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.github.Elmicass.SFJTeam_Casotto.login.LoginContextHolder;
import com.github.Elmicass.SFJTeam_Casotto.view.authentication.AuthenticationConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.managerViews.activityService.ActivityServiceManagerConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.managerViews.barService.BarServiceManagerConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.managerViews.beachService.BeachServiceManagerConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.managerViews.jobOffersService.JobOffersServiceManagerConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.managerViews.priceListsManagement.PriceListsManagerConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.usersViews.activityService.ActivityServiceUserConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.usersViews.barService.BarServiceUserConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.usersViews.beachService.BeachServiceConsole;
import com.github.Elmicass.SFJTeam_Casotto.view.usersViews.jobOffersService.JobOffersServiceUserConsole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ConsoleView implements IConsoleView {

    @Autowired
    private AuthenticationConsole authConsole;

    @Autowired
    private ActivityServiceManagerConsole actManagerConsole;

    @Autowired
    private BeachServiceManagerConsole beachManagerConsole;

    @Autowired
    private BarServiceManagerConsole barManagerConsole;

    @Autowired
    private JobOffersServiceManagerConsole joManagerConsole;

    @Autowired
    private PriceListsManagerConsole plManagerConsole;

    @Autowired
    private ActivityServiceUserConsole actUserConsole;

    @Autowired
    private BeachServiceConsole beachUserConsole;

    @Autowired
    private BarServiceUserConsole barUserConsole;

    @Autowired
    private JobOffersServiceUserConsole joUserConsole;

    private boolean open;
    
    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public ConsoleView() {
        this.commands = new HashMap<>();
        this.open = true;
        this.in = new Scanner(System.in);
    }

    public void start() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Registration or login is required to access the application features!|");
        System.out.println("| Press " 
            + ConsoleColors.GREEN + "[ENTER]" 
                    + ConsoleColors.RESET + " to proceed or type " 
                                   + ConsoleColors.RED + "[0]" 
                                         + ConsoleColors.RESET + " to quit the application.        |");
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
        this.open =  true;
        while(this.open) {
            commands.clear();
            addCommand(("0"), c -> close());
            if (LoginContextHolder.isUserLogged() == false && this.open == true)
                loginPhase();
            if (LoginContextHolder.isUserLogged() == true && this.open == true)
                mainMenuPhase();
        }

    }

    @Override
    public void close() {
        try {
            this.open = false;
            commands.clear();
            System.exit(0);   
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            System.exit(1);
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

    public void loginPhase() {
        commands.clear();
        while (!LoginContextHolder.isUserLogged()) {
            addCommand(("ENTER"), c -> authConsole.open());
            clearConsoleScreen();
            start();
            System.out.print(" > ");
            System.out.flush();
            String command;
            command = in.nextLine();
            if (command.isBlank())
                processCommand("ENTER");
            else
                processCommand(command);
        }
        commands.clear();
    }
    
    public void mainMenuPhase() {
        commands.clear();
        List<? extends GrantedAuthority> roles = LoginContextHolder.getCurrentAppUser().getAuthorities().stream().filter((auth) -> auth.getAuthority().toString().startsWith("ROLE")).collect(Collectors.toList()); 
        if (roles.stream().findAny().filter(auth -> auth.getAuthority().toString().equals("ROLE_MANAGER")).isPresent()) {
            while (LoginContextHolder.isUserLogged()) {
                addCommand("S", s -> { try { beachManagerConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                addCommand("A", s -> { try { actManagerConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                addCommand("B", s -> { try { barManagerConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                addCommand("O", s -> { try { joManagerConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                addCommand("P", s -> { try { plManagerConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                commands.put("BACK", c -> authConsole.logoutController());
                clearConsoleScreen();
                managerMenu();
                System.out.print(" > ");
                System.out.flush();
                String command = in.nextLine();
                processCommand(command);
            }
        } else if (roles.stream().findAny().filter(auth -> auth.getAuthority().toString().equals("ROLE_EMPLOYEE")).isPresent()) {
            //addCommands

            commands.put("BACK", c -> authConsole.logoutController());
            clearConsoleScreen();
            //MENU

            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
        } else if (roles.stream().findAny().filter(auth -> auth.getAuthority().toString().equals("ROLE_USER")).isPresent()) {
            while (LoginContextHolder.isUserLogged()) {
                addCommand("S", s -> { try { beachUserConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                addCommand("A", s -> { try { actUserConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                addCommand("B", s -> { try { barUserConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                addCommand("O", s -> { try { joUserConsole.open(); } catch (IOException e) { e.printStackTrace(); }});
                commands.put("BACK", c -> authConsole.logoutController());
                clearConsoleScreen();
                usersMenu();
                System.out.print(" > ");
                System.out.flush();
                String command = in.nextLine();
                processCommand(command);
            }
        }
        commands.clear();
    }

    public void usersMenu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [S] - Beach service                                                  |");
        System.out.println("| [A] - Activity service                                               |");
        System.out.println("| [B] - Bar service                                                    |");
        System.out.println("| [O] - Job offers service                                             |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back (Logout)                                            |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }

    public void managerMenu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [S] - Beach service                                                  |");
        System.out.println("| [A] - Activity service                                               |");
        System.out.println("| [B] - Bar service                                                    |");
        System.out.println("| [O] - Job offers service                                             |");
        System.out.println("|                                                                      |");
        System.out.println("| [P] - Price lists management                                         |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back (Logout)                                            |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }


   

    

    
}
