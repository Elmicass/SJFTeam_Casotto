package com.github.Elmicass.SFJTeam_Casotto.view.managerViews.jobOffersService;

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

import com.github.Elmicass.SFJTeam_Casotto.controller.IJobOfferManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IReservationManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.JobOfferPrinter;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.ReservationPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class JobOffersServiceManagerConsole implements IConsoleView {

    @Autowired
    private JobOfferPrinter joPrinter;

    @Autowired
    private IJobOfferManager joManager;

    @Autowired
    private ReservationPrinter reservationsPrinter;

    @Autowired
    private IReservationManager resManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public JobOffersServiceManagerConsole() {
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
            addCommand(("J"), c -> jobOffersListView());
            addCommand(("C"), c -> creationPhase());
            addCommand(("A"), c -> jobOffersApplicationsView());
            addCommand(("BACK"), c -> close());
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
        System.out.println("|                          Job offers service                          |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [J] - Show job offers service                                        |");
        System.out.println("| [C] - Creation menu                                                  |");
        System.out.println("| [A] - Job offer service applications                                 |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back                                                     |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }

    private void creationMenu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                            Creation menu                             |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [JO] - Create new job offer                                          |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back                                                     |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }

    private void creationPhase() {
        commands.clear();
        AtomicReference<Boolean> waiting = new AtomicReference<>(true);
        while (waiting.get()) {
            addCommand(("JO"), c -> createJobOffer());
            addCommand(("BACK"), c -> waiting.set(false));
            addCommand("0", c -> System.exit(0));
            clearConsoleScreen();    
            creationMenu();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
        }
        commands.clear();
    }

    private void jobOffersListView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            joPrinter.printListOfObjects(joManager.getAll());
            if (!(joManager.getAll().isEmpty())) {
                for (JobOffer jo : joManager.getAll()) {
                    addCommand(jo.getID().toString(), c -> { joPrinter.printFullVersion(jo); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding job offer ID number to select.");
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the job offers service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                addCommand("BACK", c -> waiting2.set(false));
                System.out.println("Type " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                        + " when you want to go back to the job offers list.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }
        }
        commands.clear();
    }

    private void createJobOffer() {
        commands.clear();
        AtomicReference<Boolean> joCreation = new AtomicReference<>(true);
        while (joCreation.get()) {
            addCommand("STOP", c -> joCreation.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                       " + ConsoleColors.PURPLE + "Job offer creation procedure"
                    + ConsoleColors.RESET + "                      |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Job offer entry fields:                                               |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" in any moment to abort the operation.                 ] |");
            System.out.println("|                                                                       |");
            System.out.println("| - Job offer name >                                                    |");
            String name = null;
            String input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (joCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    name = input;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Job offer name >                                                    |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Job offer description >                                             |");
            String description = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (joCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    description = input;
                    if (description.length() > 199)
                        throw new IllegalArgumentException("The description field cannot exceed 199 characters.");
                    break;
                } catch (IllegalArgumentException iae) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Job offer description >                                             |");
                    input = in.nextLine();
                }
            }
            System.out.println("| [Please enter dates fields in the following format: \"" + ConsoleColors.GREEN
                    + "dd-mm-yy hh:mm" + ConsoleColors.RESET + "\"] |");
            System.out.println("| - Start date and time >                                               |");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uu HH:mm").withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime startDateTime = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (joCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    startDateTime = LocalDateTime.parse(input, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Start date and time >                                               |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - End date and time >                                                 |");
            LocalDateTime endDateTime = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (joCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    endDateTime = LocalDateTime.parse(input, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - End date and time >                                                 |");
                    input = in.nextLine();
                }
            }
            if (joCreation.get()) {
                try {
                    joManager.createJobOffer(name, description, startDateTime, endDateTime);
                    System.out.println("\n" + ConsoleColors.GREEN + "SUCCESSFUL CREATION!" + ConsoleColors.RESET + "\n");
                    System.out.println("+-----------------------------------------------------------------------+");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    processCommand("STOP");
                } catch (IllegalArgumentException | AlreadyExistingException iaee) {
                    System.err.println(iaee.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                }
            }
        }
        System.out.flush();
        commands.clear();
    }

    private void jobOffersApplicationsView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            joPrinter.printListOfObjects(joManager.getAll());
            System.out.println("");
            System.out.flush();
            reservationsPrinter.printListOfObjects(resManager.getByType(BookableEntityType.JobOffer));
            if (!(resManager.getByType(BookableEntityType.JobOffer).isEmpty())) {
                for (Reservation res : resManager.getByType(BookableEntityType.JobOffer)) {
                    addCommand(res.getID().toString(), c -> { reservationsPrinter.printFullVersion(res); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding application ID number to select.");
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the job offers service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                addCommand("BACK", c -> waiting2.set(false));
                System.out.println("Type " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                        + " when you want to go back to the applications list.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }
        }
        commands.clear();
    }

    
}
