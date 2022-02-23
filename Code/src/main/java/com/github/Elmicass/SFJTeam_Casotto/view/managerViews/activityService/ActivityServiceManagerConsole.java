package com.github.Elmicass.SFJTeam_Casotto.view.managerViews.activityService;

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

import com.github.Elmicass.SFJTeam_Casotto.controller.IActivityManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IEquipmentManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IReservationManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.ActivityPrinter;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.EquipmentPrinter;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.ReservationPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ActivityServiceManagerConsole implements IConsoleView {

    @Autowired
    private ActivityPrinter activityPrinter;

    @Autowired
    private EquipmentPrinter eqPrinter;

    @Autowired
    private ReservationPrinter reservationsPrinter;

    @Autowired
    private IActivityManager actManager;

    @Autowired
    private IEquipmentManager eqManager;

    @Autowired
    private IReservationManager resManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public ActivityServiceManagerConsole() {
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
            addCommand(("C"), c -> creationPhase());
            addCommand(("R"), c -> activityReservationsView());
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

    private void menu() {
        clearConsoleScreen();
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                 |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                          Activities service                          |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [A] - Show activity service                                          |");
        System.out.println("| [C] - Creation menu                                                  |");
        System.out.println("| [R] - Activity service reservations                                  |");
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
        System.out.println("| [EQ] - Create new equipment                                          |");
        System.out.println("| [AC] - Create new activity                                           |");
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
            addCommand(("EQ"), c -> createEquipment());
            addCommand(("AC"), c -> createActivity());
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
    
    private void activityListView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            eqPrinter.printListOfObjects(eqManager.getAll());
            System.out.println("");
            activityPrinter.printListOfObjects(actManager.getAll());
            if (!(eqManager.getAll().isEmpty())) {
                for (Equipment eq : eqManager.getAll()) {
                    addCommand("EQ"+eq.getID().toString(), c -> { eqPrinter.printFullVersion(eq); waiting2.set(true); });
                }
            }
            if (!(actManager.getAll().isEmpty())) {
                for (Activity act : actManager.getAll()) {
                    addCommand("AC"+act.getID().toString(), c -> { activityPrinter.printFullVersion(act); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType EQ* or AC* with a number(corresponding ID) instead of the asterisk to select an Equipment or an Activity.");
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the activity service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                if (command.startsWith("AC")) {
                    String id = command.substring(2);
                    addCommand("DELETE", c -> { deleteActivity(id); waiting2.set(false); });
                    System.out.println(
                        "\nType " + ConsoleColors.RED + "DELETE" + ConsoleColors.RESET + " to eliminate this activity and all bookings to it.\n");
                    System.out.flush();
                }
                if (command.startsWith("EQ")) {
                    String id = command.substring(2);
                    addCommand("DELETE", c -> { deleteEquipment(id); waiting2.set(false); });
                    System.out.println(
                        "\nType " + ConsoleColors.RED + "DELETE" + ConsoleColors.RESET + " to eliminate this equipment (only if no activities are scheduled to use it).\n");
                    System.out.flush();
                }
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

    private void createEquipment() {
        AtomicReference<Boolean> eqCreation = new AtomicReference<>(true);
        while (eqCreation.get()) {
            addCommand("STOP", c -> eqCreation.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                       " + ConsoleColors.WHITE + "Equipment creation procedure"
                    + ConsoleColors.RESET + "                    |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Equipment entry fields:                                               |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" in any moment to abort the operation.               ]   |");
            System.out.println("|                                                                       |");
            System.out.println("| - Equipment name >                                                    |");
            String name = null;
            String command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (eqCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    name = command;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Equipment name >                                                    |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - Equipment description >                                             |");
            String description = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (eqCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    description = command;
                    if (description.length() > 199)
                        throw new IllegalArgumentException("The description field cannot exceed 199 characters.");
                    break;
                } catch (IllegalArgumentException iae) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Equipment description >                                             |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - Equipment type (Indoor, Outdoor) >                                  |");
            String type = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (eqCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    type = command;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Equipment type (Indoor, Outdoor) >                                  |");
                    command = in.nextLine();
                }
            }
            if (eqCreation.get()) {
                try {
                    eqManager.createEquipment(name, description, type);
                    System.out.println("\n" + ConsoleColors.GREEN + "SUCCESSFUL CREATION!" + ConsoleColors.RESET + "\n");
                    System.out.println("+-----------------------------------------------------------------------+");
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {}
                    processCommand("STOP");
                } catch (IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {}
                }
            }

        }    
        System.out.flush();
    }

    private void createActivity() {
        AtomicReference<Boolean> actCreation = new AtomicReference<>(true);
        while (actCreation.get()) {
            addCommand("STOP", c -> actCreation.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                        " + ConsoleColors.PURPLE + "Activity creation procedure"
                    + ConsoleColors.RESET + "                    |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Activity entry fields:                                                |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" in any moment to abort the operation.               ]   |");
            System.out.println("|                                                                       |");
            System.out.println("| - Activity name >                                                     |");
            String name = null;
            String command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (actCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    name = command;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Activity name >                                                    |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - Activity description >                                              |");
            String description = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (actCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    description = command;
                    if (description.length() > 199)
                        throw new IllegalArgumentException("The description field cannot exceed 199 characters.");
                    break;
                } catch (IllegalArgumentException iae) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Activity description >                                              |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - Max reservation entries >                                           |");
            Integer maxEntries = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (actCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    maxEntries = Integer.valueOf(command);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Max reservation entries >                                           |");
                    command = in.nextLine();
                }
            }
            System.out.println("| [Please enter dates fields in the following format: \"" + ConsoleColors.GREEN
                    + "dd-mm-yy hh:mm" + ConsoleColors.RESET + "\"] |");
            System.out.println("| - Start date and time >                                               |");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uu HH:mm").withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime startDateTime = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (actCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    startDateTime = LocalDateTime.parse(command, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Start date and time >                                               |");
                    command = in.nextLine();
                }
            }
            System.out.println("| - End date and time >                                                 |");
            LocalDateTime endDateTime = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (actCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    endDateTime = LocalDateTime.parse(command, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - End date and time >                                                 |");
                    command = in.nextLine();
                }
            }
            System.out.println("| [Please enter the equipment names separated by a comma: \"" + ConsoleColors.GREEN
                    + "," + ConsoleColors.RESET + "\"]          |");
            System.out.println("| - Equipments used >                                                   |");
            String[] equipments = null;
            command = in.nextLine();
            if (command.equals("STOP")) {
                processCommand(command);
                break;
            }
            while (actCreation.get()) {
                try {
                    if (command.equals("STOP")) {
                        processCommand(command);
                        break;
                    }
                    equipments = command.split(",");
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Equipments used >                                                   |");
                    command = in.nextLine();
                }
            }
            if (actCreation.get()) {
                try {
                    actManager.createNewActivity(name, description, maxEntries, startDateTime, endDateTime, equipments);
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
                    } catch (Exception e) {
                    }
                }
            }
        }
        System.out.flush();
    }

    private void activityReservationsView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            activityPrinter.printListOfObjects(actManager.getAll());
            System.out.println("");
            System.out.flush();
            reservationsPrinter.printListOfObjects(resManager.getByType(BookableEntityType.Activity));
            if (!(resManager.getByType(BookableEntityType.Activity).isEmpty())) {
                for (Reservation res : resManager.getByType(BookableEntityType.Activity)) {
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

    private void deleteActivity(String idString) {
        AtomicReference<Boolean> actCancellation = new AtomicReference<>(true);
        AtomicReference<Boolean> delete = new AtomicReference<>(false);
        while (actCancellation.get()) {
            addCommand("STOP", c -> actCancellation.set(false));
            addCommand("DELETE", c -> delete.set(true));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                     " + ConsoleColors.RED + "Activity elimination procedure"
                    + ConsoleColors.RESET + "                    |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Activity elimination:                                                 |");
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
                    actManager.delete(id);
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
    
    private void deleteEquipment(String idString) {
        AtomicReference<Boolean> eqCancellation = new AtomicReference<>(true);
        AtomicReference<Boolean> delete = new AtomicReference<>(false);
        while (eqCancellation.get()) {
            addCommand("STOP", c -> eqCancellation.set(false));
            addCommand("DELETE", c -> delete.set(true));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                    " + ConsoleColors.RED + "Equipment elimination procedure"
                    + ConsoleColors.RESET + "                    |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Equipment elimination:                                                |");
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
                    eqManager.delete(id);
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
