package com.github.Elmicass.SFJTeam_Casotto.view.usersViews.beachService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.controller.IBeachPlaceManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IReservationManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.ISeaRowManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.UsersManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.login.LoginContextHolder;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.BeachPlacePrinter;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.SeaRowsPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeachServiceConsole implements IConsoleView {

    @Autowired
    private BeachPlacePrinter beachPlacesPrinter;

    @Autowired
    private SeaRowsPrinter seaRowsPrinter;

    @Autowired
    private IBeachPlaceManager beachPlacesManager;

    @Autowired
    private ISeaRowManager seaRowManager;

    @Autowired
    private IReservationManager reservationsManager;

    @Autowired
    private UsersManager usersManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public BeachServiceConsole() {
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
            addCommand(("S"), c -> beachStructureView());
            addCommand(("BACK"), c -> { try { this.close(); } catch (IOException e) { e.printStackTrace(); }});
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
    public void close() throws IOException {
        this.open = false;
        commands.clear();   
    }

    public void menu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                            Beach service                             |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Press [?] for:                                                       |");
        System.out.println("| [S] - Show beach structure                                           |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back                                                     |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
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

    private void beachStructureView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            seaRowsPrinter.beachStructure(seaRowManager.getAll());
            System.out.println("");
            System.out.flush();
            beachPlacesPrinter.printListOfObjects(beachPlacesManager.getAll());
            if (!(beachPlacesManager.getAll().isEmpty())) {
                for (BeachPlace bp : beachPlacesManager.getAll()) {
                    addCommand(bp.getID().toString(), c -> { beachPlacesPrinter.printFullVersion(bp); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the beach service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                addCommand("BACK", c -> waiting2.set(false));
                addCommand("B", c -> bookBeachPlace(Integer.valueOf(command)));
                System.out.println(
                        "\nType " + ConsoleColors.GREEN + "B" + ConsoleColors.RESET + " to book this beach place.\n");
                System.out.flush();
                System.out.println("Type " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                        + " when you want to go back to the beach places list.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }

        }
        commands.clear();
    }

    public void bookBeachPlace(Integer idString) {
        AtomicReference<Boolean> booking = new AtomicReference<>(true);
        while (booking.get()) {
            addCommand("STOP", c -> booking.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                    " + ConsoleColors.YELLOW + "Beach place reservation procedure"
                    + ConsoleColors.RESET + "                  |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Booking period entry fields:                                          |");
            System.out.println("|                                                                       |");
            System.out.println("| [Please enter dates fields in the following format: \"" + ConsoleColors.GREEN
                    + "dd-mm-yy hh:mm" + ConsoleColors.RESET + "\"] |");
            System.out.println("| [Or enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" to abort the operation                             ] |");
            System.out.println("|                                                                       |");
            System.out.println("| - booking start date >                                                |");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uu HH:mm").withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime startDateTime = null;
            String command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (booking.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    startDateTime = LocalDateTime.parse(command, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - booking start date >                                                |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - booking end date >                                                  |");
            LocalDateTime endDateTime = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (booking.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    endDateTime = LocalDateTime.parse(command, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - booking end date >                                                  |");
                    command = in.nextLine();
                }
            }
            if (booking.get()) {
                try {
                    reservationsManager.booking(BookableEntityType.BeachPlace.name(),
                        usersManager.getUserByEmail(LoginContextHolder.getCurrentAppUser().getUsername()), idString,
                        startDateTime, endDateTime);
                    System.out.println("\n" + ConsoleColors.GREEN + "YOU SUCCESSFULLY BOOKED!" + ConsoleColors.RESET + "\n");
                    System.out.println("+-----------------------------------------------------------------------+");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    processCommand("STOP");
                } catch (IllegalStateException ise) {
                    System.err.println(ise.getMessage());
                    continue;
                } catch (AlreadyExistingException | EntityNotFoundException exceptions) {
                    System.err.println(exceptions.getMessage());
                    break;
                }
            }
        }
        System.out.flush();
    }






    
    
}
