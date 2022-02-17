package com.github.Elmicass.SFJTeam_Casotto.view.usersViews.barService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.controller.IUserManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.IProductManager;
import com.github.Elmicass.SFJTeam_Casotto.model.Product;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.ProductPrinter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BarServiceUserConsole implements IConsoleView {

    @Autowired
    private ProductPrinter prodPrinter;

    @Autowired
    private IProductManager productManager;

    @Autowired
    private IUserManager usersManager;

    private boolean open;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public BarServiceUserConsole() {
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
            addCommand(("B"), c -> barServiceListView());
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
        System.out.println("|                             Bar service                              |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.println("|                                                                      |");
        System.out.println("| Type [?] for:                                                        |");
        System.out.println("| [B] - Show bar service                                               |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACK] - Go back                                                     |");
        System.out.println("| [0] - Quit application                                               |");
        System.out.println("+----------------------------------------------------------------------+");
        System.out.flush();
    }

    private void barServiceListView() {
        commands.clear();
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(false);
        while (waiting1.get()) {
            clearConsoleScreen();
            prodPrinter.printListOfObjects(productManager.getAvailableProducts());
            if (!(productManager.getAvailableProducts().isEmpty())) {
                for (Product pr : productManager.getAvailableProducts()) {
                    addCommand(pr.getID().toString(), c -> { prodPrinter.printFullVersion(pr); waiting2.set(true); });
                }
            }
            addCommand("BACK", c -> waiting1.set(false));
            System.out.println("\nType the corresponding bar product ID number to select.");
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
                        + " when you want to go back to bar service list.");
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
