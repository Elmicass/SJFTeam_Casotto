package com.github.Elmicass.SFJTeam_Casotto.view.usersViews.activityService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.controller.IActivityManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IReservationManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IUserManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.login.LoginContextHolder;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.ActivityPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ActivityServiceUserConsole implements IConsoleView {

    @Autowired
    private ActivityPrinter activityPrinter;

    @Autowired
    private IActivityManager actManager;

    @Autowired
    private IReservationManager reservationsManager;

    @Autowired
    private IUserManager usersManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public ActivityServiceUserConsole() {
        this.commands = new HashMap<>();
        this.in = new Scanner(System.in);
        this.open = true;
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void open() throws IOException {
        commands.clear();
        this.open = true;
        while (open) {
            addCommand(("A"), c -> activityListView());
            addCommand(("BACK"), c -> this.close());
            addCommand("0", c -> System.exit(0));
            clearConsoleScreen();    
            menu();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
        }
    }

    @Override
    public void close() {
        this.open = false;
        commands.clear();
    }

    @Override
    public void processCommand(String commandName) {
        try {
            Consumer<? super IConsoleView> command = this.commands.get(commandName);
            if (command == null) {
                System.err.println("Unknown command: " + commandName);
            } else {
                clearConsoleScreen();
                command.accept(this);
            }
        } catch (NumberFormatException e) {
            System.err.println("Unknown command: " + commandName);
        }
    }

    private void addCommand(String key, Consumer<? super IConsoleView> command) {
        commands.put(key, command);
    }

    public void menu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                          Activities service                          |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [A] - Show activity service                                          |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back                                                     |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }

    private void activityListView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            activityPrinter.printListOfObjects(actManager.getAll());
            if (!(actManager.getAll().isEmpty())) {
                for (Activity act : actManager.getAll()) {
                    addCommand(act.getID().toString(), c -> { activityPrinter.printFullVersion(act); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding activity ID number to select.");
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the activity service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                addCommand("BACK", c -> waiting2.set(false));
                System.out.println("Type " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                        + " when you want to go back to the activities list.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }
        }
        commands.clear();
    }

    public void bookBeachPlace(Integer command) {
        commands.clear();
        AtomicReference<Boolean> waiting = new AtomicReference<>(true);
        while (waiting.get()) {
            addCommand("STOP", c -> waiting.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                     " + ConsoleColors.YELLOW + "Activity reservation procedure"
                    + ConsoleColors.RESET + "                   |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Booking period entry fields:                                          |");
            System.out.println("|                                                                       |");
            System.out.println("| [Please enter dates fields in the following format: \"" + ConsoleColors.GREEN
                    + "dd-mm-yy hh:mm" + ConsoleColors.RESET + "\"]   |");
            System.out.println("| [Or enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" to abort the operation                           ] |");
            System.out.println("|                                                                       |");
            try {
                reservationsManager.booking(BookableEntityType.Activity.name(),
                        usersManager.getUserByEmail(LoginContextHolder.getCurrentAppUser().getUsername()), command,
                        actManager.getInstance(command).getTimeSlot().getStart(), actManager.getInstance(command).getTimeSlot().getStop());
                System.out.println("\n" + ConsoleColors.GREEN + "YOU SUCCESSFULLY BOOKED!" + ConsoleColors.RESET + "\n");
                System.out.println("+-----------------------------------------------------------------------+");
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {}
                processCommand("STOP");
            } catch (IllegalStateException ise) {
                System.err.println(ise.getMessage());
                break;
            } catch (AlreadyExistingException | EntityNotFoundException exceptions) {
                System.err.println(exceptions.getMessage());
                break;
            }
        }
        System.out.flush();
        commands.clear();
    }

    



    
}
