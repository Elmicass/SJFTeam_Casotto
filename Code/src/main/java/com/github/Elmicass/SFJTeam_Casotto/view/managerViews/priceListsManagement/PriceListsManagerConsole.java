package com.github.Elmicass.SFJTeam_Casotto.view.managerViews.priceListsManagement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.controller.PriceListsManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.PriceListPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceListsManagerConsole implements IConsoleView {

    @Autowired
    private PriceListPrinter priceListPrinter;

    @Autowired
    private PriceListsManager plManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public PriceListsManagerConsole() {
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
            addCommand(("P"), c -> priceListsView());
            addCommand(("C"), c -> creationPhase());
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
        System.out.println("|                       Price lists management                         |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [P] - Show price lists                                               |");
        System.out.println("| [C] - Creation menu                                                  |");
        System.out.println("|                                                                      |");
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
        System.out.println("| [PL] - Create new price list                                         |");
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
            addCommand(("PL"), c -> createPriceList());
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

    private void priceListsView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            priceListPrinter.printListOfObjects(plManager.getAll());
            if (!(plManager.getAll().isEmpty())) {
                for (PriceList pl : plManager.getAll()) {
                    addCommand(pl.getID().toString(), c -> { priceListPrinter.printFullVersion(pl); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding price list ID number to select");
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
                        + " when you want to go back to the price lists.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }
        }
        commands.clear();
    }

    private void createPriceList() {
        commands.clear();
        AtomicReference<Boolean> plCreation = new AtomicReference<>(true);
        while (plCreation.get()) {
            addCommand("STOP", c -> plCreation.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                     " + ConsoleColors.YELLOW_BRIGHT + "Price list creation procedure"
                    + ConsoleColors.RESET + "                     |");
            System.out.println("+----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Price list entry fields:                                              |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" in any moment to abort the operation.                 ] |");
            System.out.println("|                                                                       |");
            System.out.println("| - Price list name >                                                   |");
            String name = null;
            String input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (plCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    name = input;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Price list name >                                                   |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Sunbed hourly price >                                               |");
            Double sunbedHourlyPrice = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (plCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    sunbedHourlyPrice = Double.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Sunbed hourly price >                                               |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Small sunshades hourly price >                                              |");
            Double smallSHourlyPrice = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (plCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    smallSHourlyPrice = Double.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Small sunshades hourly price >                                              |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Medium sunshades hourly price >                                             |");
            Double mediumSHourlyPrice = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (plCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    mediumSHourlyPrice = Double.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Medium sunshades hourly price >                                             |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Large sunshades hourly price >                                              |");
            Double largeSHourlyPrice = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (plCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    largeSHourlyPrice = Double.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Large sunshades hourly price >                                              |");
                    input = in.nextLine();
                }
            }
            if (plCreation.get()) {
                try {
                    plManager.createPriceList(name, sunbedHourlyPrice, smallSHourlyPrice, mediumSHourlyPrice, largeSHourlyPrice);
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
    
}
