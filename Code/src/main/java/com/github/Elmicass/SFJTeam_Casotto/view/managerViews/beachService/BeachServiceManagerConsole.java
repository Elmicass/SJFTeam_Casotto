package com.github.Elmicass.SFJTeam_Casotto.view.managerViews.beachService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.controller.IBeachPlaceManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IReservationManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.ISeaRowManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.BeachPlacePrinter;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.ReservationPrinter;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.SeaRowsPrinter;
import com.google.zxing.WriterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeachServiceManagerConsole implements IConsoleView {

    @Autowired
    private BeachPlacePrinter beachPlacesPrinter;

    @Autowired
    private SeaRowsPrinter srPrinter;

    @Autowired
    private ReservationPrinter reservationsPrinter;

    @Autowired
    private IBeachPlaceManager bpManager;

    @Autowired
    private ISeaRowManager srManager;

    @Autowired
    private IReservationManager resManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public BeachServiceManagerConsole() {
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
            addCommand(("C"), c -> creationPhase());
            addCommand(("R"), c -> beachReservationsView());
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
        System.out.println("|                            Beach service                             |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [S] - Show beach structure                                           |");
        System.out.println("| [C] - Creation menu                                                  |");
        System.out.println("| [R] - Beach service reservations                                     |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back                                                     |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }

    private void beachStructureView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            srPrinter.beachStructure(srManager.getAll());
            srPrinter.printListOfObjects(srManager.getAll());
            beachPlacesPrinter.printListOfObjects(bpManager.getAll());
            if (!(srManager.getAll().isEmpty())) {
                for (SeaRow sr : srManager.getAll()) {
                    addCommand("SR"+sr.getID().toString(), c -> { srPrinter.printFullVersion(sr); waiting2.set(true); });
                }
            }
            if (!(bpManager.getAll().isEmpty())) {
                for (BeachPlace bp : bpManager.getAll()) {
                    addCommand("BP"+bp.getID().toString(), c -> { beachPlacesPrinter.printFullVersion(bp); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType SR* or BP* with a number(corresponding ID) instead of the asterisk to select a Sea Row or a Beach Place.");
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the beach service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                if (command.startsWith("BP")) {
                    String id = command.substring(2);
                    addCommand("DELETE", c -> { deleteBeachPlace(id); waiting2.set(false); });
                    System.out.println(
                        "\nType " + ConsoleColors.RED + "DELETE" + ConsoleColors.RESET + " to eliminate this beach place and all bookings to it.\n");
                    System.out.flush();
                }
                if (command.startsWith("SR")) {
                    String id = command.substring(2);
                    addCommand("DELETE", c -> { deleteSeaRow(id); waiting2.set(false); });
                    System.out.println(
                        "\nType " + ConsoleColors.RED + "DELETE" + ConsoleColors.RESET + " to eliminate this sea row (only if empty).\n");
                    System.out.flush();
                }
                addCommand("BACK", c -> waiting2.set(false));
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

    private void creationMenu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                            Creation menu                             |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [SR] - Create new sea row                                            |");
        System.out.println("| [BP] - Create new beach place                                        |");
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
            addCommand(("SR"), c -> createSeaRow());
            addCommand(("BP"), c -> createBeachPlace());
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

    private void createSeaRow() {
        AtomicReference<Boolean> srCreation = new AtomicReference<>(true);
        while (srCreation.get()) {
            addCommand("STOP", c -> srCreation.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                        " + ConsoleColors.BLUE_BRIGHT + "Sea row creation procedure"
                    + ConsoleColors.RESET + "                     |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Sea row entry fields:                                                 |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" in any moment to abort the operation.               ]   |");
            System.out.println("|                                                                       |");
            System.out.println("| - Sea row number >                                                    |");
            Integer seaRowNumber = null;
            String input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (srCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    seaRowNumber = Integer.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.print("| - Sea row number >                                                    |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Max number of beach places for this sea row >                       |");
            Integer maxBPs = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (srCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    maxBPs = Integer.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.print("| - Max number of beach places for this sea row >                       |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Fixed price per sea row (optional) >                                |");
            Double price = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (srCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    price = Double.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.print("| - Fixed price per sea row (optional) >                                |");
                    input = in.nextLine();
                }
            }
            if (srCreation.get()) {
                try {
                    srManager.createSeaRow(seaRowNumber, maxBPs, price);
                    System.out.println("\n" + ConsoleColors.GREEN + "SUCCESSFUL CREATION!" + ConsoleColors.RESET + "\n");
                    System.out.println("+-----------------------------------------------------------------------+");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    processCommand("STOP");
                } catch (AlreadyExistingException | IllegalStateException exceptions) {
                    System.err.println(exceptions.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                    break;
                }
            }
        }
        System.out.flush();
    }

    private void createBeachPlace() {
        AtomicReference<Boolean> bpCreation = new AtomicReference<>(true);
        while (bpCreation.get()) {
            addCommand("STOP", c -> bpCreation.set(false));
            clearConsoleScreen();
            System.out.println("+-------------------------------------------------------------------------------+");
            System.out.println("|                      " + ConsoleColors.YELLOW + "Beach place creation procedure"
                    + ConsoleColors.RESET + "                            |");
            System.out.println("+-------------------------------------------------------------------------------+");
            System.out.println("|                                                                               |");
            System.out.println("| Beach place entry fields:                                                     |");
            System.out.println("|                                                                               |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" in any moment to abort the operation.                       ]   |");
            System.out.println("|                                                                               |");
            System.out.println("| - Sea row number >                                                            |");
            Integer seaRowNumber = null;
            String command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (bpCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    seaRowNumber = Integer.valueOf(command);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Sea row number >                                                            |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - Sea row position number >                                                   |");
            Integer position = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (bpCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    position = Integer.valueOf(command);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Sea row position number >                                                   |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - Sunshade type (Small, Medium, Large) >                                      |");
            String type = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (bpCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    type = command;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Sunshade type (SMALL, MEDIUM, LARGE) >                                      |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - Sunbeds number >                                                            |");
            Integer sunbedsNumber = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (bpCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    sunbedsNumber = Integer.valueOf(command);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Sunbeds number >                                                            |");
                    command = in.nextLine();
                }
            }
            System.out.println("| (If the specified price list does not exist, the default one will be applied) |");
            System.out.println("| - Price list name to be applied >                                             |");
            String priceListName = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (bpCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    priceListName = command;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Price list name to be applied >                                             |");
                    command = in.nextLine();
                }
            }
            if (bpCreation.get()) {
                try {
                    bpManager.createBeachPlace(seaRowNumber, position, priceListName, type, sunbedsNumber);
                    System.out.println("\n" + ConsoleColors.GREEN + "SUCCESSFUL CREATION!" + ConsoleColors.RESET + "\n");
                    System.out.println("+-------------------------------------------------------------------------------+");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    processCommand("STOP");
                } catch (IllegalArgumentException | ReachedLimitOfObjects exceptions) {
                    System.err.println(exceptions.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                } catch (WriterException | IOException ioe) {
                    System.err.println(ioe.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                    break;
                } catch (IllegalStateException | AlreadyExistingException ise) {
                    System.err.println(ise.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                    break;
                }
            }
        }
        System.out.flush();
    }

    private void beachReservationsView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            srPrinter.beachStructure(srManager.getAll());
            System.out.println("");
            System.out.flush();
            reservationsPrinter.printListOfObjects(resManager.getByType(BookableEntityType.BeachPlace));
            if (!(resManager.getByType(BookableEntityType.BeachPlace).isEmpty())) {
                for (Reservation res : resManager.getByType(BookableEntityType.BeachPlace)) {
                    addCommand(res.getID().toString(), c -> { reservationsPrinter.printFullVersion(res); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding reservation ID number to select.");
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
                System.out.println("Type " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                        + " when you want to go back to the reservations list.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }
        }
        commands.clear();
    }

    private void deleteBeachPlace(String idString) {
        AtomicReference<Boolean> bpCancellation = new AtomicReference<>(true);
        AtomicReference<Boolean> delete = new AtomicReference<>(false);
        while (bpCancellation.get()) {
            addCommand("STOP", c -> bpCancellation.set(false));
            addCommand("DELETE", c -> delete.set(true));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                   " + ConsoleColors.RED + "Beach place elimination procedure"
                    + ConsoleColors.RESET + "                   |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Beach place elimination:                                              |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET + "\" NOW to abort the operation.                           ] |");
            System.out.println("|                                                                       |");
            System.out.println("| [Or confirm the operation by typing \"" + ConsoleColors.RED + "DELETE" + ConsoleColors.RESET + "\".                       ] |");
            System.out.println("|                                                                       |");
            System.out.println("| ->                                                                    |");
            String command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            processCommand(command);
            if (delete.get()) {
                try {
                    Integer id = Integer.valueOf(idString);
                    bpManager.delete(id);
                    System.out.println("\n" + ConsoleColors.GREEN + "SUCCESSFULLY DELEATED!" + ConsoleColors.RESET + "\n");
                    System.out.println("+-----------------------------------------------------------------------+");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    processCommand("STOP");
                } catch (EntityNotFoundException | IllegalArgumentException exceptions) {
                    System.err.println(exceptions.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                    break;
                }
            }
        }
    }

    private void deleteSeaRow(String idString) {
        AtomicReference<Boolean> srCancellation = new AtomicReference<>(true);
        AtomicReference<Boolean> delete = new AtomicReference<>(false);
        while (srCancellation.get()) {
            addCommand("STOP", c -> srCancellation.set(false));
            addCommand("DELETE", c -> delete.set(true));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                     " + ConsoleColors.RED + "Sea row elimination procedure"
                    + ConsoleColors.RESET + "                     |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Sea row elimination:                                                  |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET + "\" NOW to abort the operation.                           ] |");
            System.out.println("|                                                                       |");
            System.out.println("| [Or confirm the operation by typing \"" + ConsoleColors.RED + "DELETE" + ConsoleColors.RESET + "\".                       ] |");
            System.out.println("|                                                                       |");
            System.out.println("| ->                                                                    |");
            String command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            processCommand(command);
            if (delete.get()) {
                try {
                    Integer id = Integer.valueOf(idString);
                    srManager.delete(id);
                    System.out.println("\n" + ConsoleColors.GREEN + "SUCCESSFULLY DELEATED!" + ConsoleColors.RESET + "\n");
                    System.out.println("+-----------------------------------------------------------------------+");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    processCommand("STOP");
                } catch (EntityNotFoundException | IllegalArgumentException | IllegalStateException exceptions) {
                    System.err.println(exceptions.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                    break;
                }
            }
        }
    }

}