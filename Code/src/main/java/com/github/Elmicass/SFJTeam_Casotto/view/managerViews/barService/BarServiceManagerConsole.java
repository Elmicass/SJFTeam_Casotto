package com.github.Elmicass.SFJTeam_Casotto.view.managerViews.barService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.controller.IOrderManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IProductManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.model.Order;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.OrderPrinter;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.ProductPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BarServiceManagerConsole implements IConsoleView {

    @Autowired
    private ProductPrinter productsPrinter;

    @Autowired
    private OrderPrinter ordersPrinter;

    @Autowired
    private IProductManager prodManager;

    @Autowired
    private IOrderManager orderManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public BarServiceManagerConsole() {
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
            addCommand(("B"), c -> productListView());
            addCommand(("C"), c -> creationPhase());
            addCommand(("O"), c -> barOrdinationsView());
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
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                  |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                             Bar service                               |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                                                                       |");
        System.out.println("| Type [?] for:                                                         |");
        System.out.println("| [B] - Show bar service                                                |");
        System.out.println("| [C] - Creation menu                                                   |");
        System.out.println("| [O] - Bar service ordinations                                         |");
        System.out.println("|                                                                       |");
        System.out.println("| [BACK] - Go back                                                      |");
        System.out.println("| [0] - Quit application                                                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.flush();
    }

    private void creationMenu() {
        clearConsoleScreen();
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("| Casotto Smart Chalet                                                  |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                            Creation menu                              |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.println("|                                                                       |");
        System.out.println("| Type [?] for:                                                         |");
        System.out.println("| [PR] - Create new product                                             |");
        System.out.println("|                                                                       |");
        System.out.println("|                                                                       |");
        System.out.println("|                                                                       |");
        System.out.println("| [BACK] - Go back                                                      |");
        System.out.println("| [0] - Quit application                                                |");
        System.out.println("+-----------------------------------------------------------------------+");
        System.out.flush();
    }

    private void creationPhase() {
        commands.clear();
        AtomicReference<Boolean> waiting = new AtomicReference<>(true);
        while (waiting.get()) {
            addCommand(("PR"), c -> createProduct());
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

    private void productListView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            productsPrinter.printListOfObjects(prodManager.getAll());
            if (!(prodManager.getAll().isEmpty())) {
                for (Product prod : prodManager.getAll()) {
                    addCommand(prod.getID().toString(), c -> { productsPrinter.printFullVersion(prod); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding product ID number to select");
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the bar service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                addCommand("BACK", c -> waiting2.set(false));
                addCommand("DELETE", c -> { deleteProduct(command); waiting2.set(false); });
                System.out.println("\nType " + ConsoleColors.RED + "DELETE" + ConsoleColors.RESET + " to eliminate this product.\n");
                System.out.flush();
                System.out.println("Type " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                        + " when you want to go back to the products list.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }
        }
        commands.clear();
    }

    private void createProduct() {
        AtomicReference<Boolean> prodCreation = new AtomicReference<>(true);
        while (prodCreation.get()) {
            addCommand("STOP", c -> prodCreation.set(false));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                        " + ConsoleColors.CYAN + "Product creation procedure"
                    + ConsoleColors.RESET + "                     |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Product entry fields:                                                 |");
            System.out.println("|                                                                       |");
            System.out.println("| [Enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET
                    + "\" in any moment to abort the operation.                 ] |");
            System.out.println("|                                                                       |");
            System.out.println("| - Product name >                                                      |");
            String name = null;
            String input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (prodCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    name = input;
                    break;
                } catch (Exception e) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Product name >                                                      |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Product description >                                               |");
            String description = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (prodCreation.get()) {
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
                    System.out.println("| - Product description >                                               |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Product price >                                                     |");
            Double price = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (prodCreation.get()) {
                try {
                    price = Double.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Product price >                                                     |");
                    input = in.nextLine();
                }
            }
            System.out.println("| - Product quantity >                                                  |");
            Integer quantity = null;
            input = in.nextLine();
            if (input.equals("STOP")) {
                processCommand(input);
                break;
            }
            while (prodCreation.get()) {
                try {
                    if (input.equals("STOP")) {
                        processCommand(input);
                        break;
                    }
                    quantity = Integer.valueOf(input);
                    break;
                } catch (NumberFormatException nfe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.println("| - Product quantity >                                                  |");
                    input = in.nextLine();
                }
            }
            if (prodCreation.get()) {
                try {
                    prodManager.createNewProduct(name, description, price, quantity);
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
    }

    private void barOrdinationsView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            ordersPrinter.printListOfObjects(orderManager.getAll());
            if (!(orderManager.getAll().isEmpty())) {
                for (Order order : orderManager.getAll()) {
                    addCommand(order.getID().toString(), c -> { ordersPrinter.printFullVersion(order); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding order ID number to select.");
            System.out.println("\nType " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                    + " when you want to go back to the bar service menu.");
            System.out.flush();
            System.out.print(" > ");
            System.out.flush();
            String command = in.nextLine();
            processCommand(command);
            while (waiting2.get()) {
                processCommand(command);
                addCommand("BACK", c -> waiting2.set(false));
                System.out.println("Type " + ConsoleColors.RED + "BACK" + ConsoleColors.RESET
                        + " when you want to go back to the orders list.");
                System.out.flush();
                System.out.print(" > ");
                System.out.flush();
                String command2 = in.nextLine();
                processCommand(command2);
            }
        }
        commands.clear();
    }

    private void deleteProduct(String idString) {
        AtomicReference<Boolean> prCancellation = new AtomicReference<>(true);
        AtomicReference<Boolean> delete = new AtomicReference<>(false);
        while (prCancellation.get()) {
            addCommand("STOP", c -> prCancellation.set(false));
            addCommand("DELETE", c -> delete.set(true));
            clearConsoleScreen();
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                      " + ConsoleColors.RED + "Product elimination procedure"
                    + ConsoleColors.RESET + "                    |");
            System.out.println("+-----------------------------------------------------------------------+");
            System.out.println("|                                                                       |");
            System.out.println("| Product elimination:                                                  |");
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
                    prodManager.delete(id);
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
    
}
