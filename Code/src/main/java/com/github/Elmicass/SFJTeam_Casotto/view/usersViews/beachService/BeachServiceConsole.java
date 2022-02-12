package com.github.Elmicass.SFJTeam_Casotto.view.usersViews.beachService;

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
import com.github.Elmicass.SFJTeam_Casotto.controller.BeachPlacesManager;
import com.github.Elmicass.SFJTeam_Casotto.controller.ReservationsManager;
import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.login.LoginContextHolder;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.view.ConsoleColors;
import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.Elmicass.SFJTeam_Casotto.view.events.GlobalKeyListener;
import com.github.Elmicass.SFJTeam_Casotto.view.toStrings.BeachPlacePrinter;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class BeachServiceConsole implements IConsoleView {

    @Autowired
    private BeachPlacePrinter beachPlacesPrinter;

    @Autowired
    private BeachPlacesManager beachPlacesManager;

    @Autowired
    private ReservationsManager reservationsManager;

    private boolean open = true;

    private final Map<String, Consumer<? super IConsoleView>> commands;

    private Scanner in;

    public BeachServiceConsole() {
        this.commands = new HashMap<>();
        this.in = new Scanner(System.in);
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void open() {
        this.open = true;
        while(open) {
            clearConsoleScreen();
            addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_S), c -> beachStructureView());
            addCommand(NativeKeyEvent.getKeyText(NativeKeyEvent.VC_BACKSPACE), c -> close());
            menu();
            try {
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeKeyListener(new GlobalKeyListener(commands, this));
            } catch (NativeHookException nhe) {
                nhe.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        this.open = false;
        in.close();
        commands.clear();
    }

    public void menu() {
        clearConsoleScreen();
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Casotto Smart Chalet                                                 ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════╣");
        System.out.println("║                            Beach service                             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        System.out.println("┌                                                                      ┐");
        System.out.println("| Press [?] for:                                                       |");
        System.out.println("| [S] - Show beach structure                                           |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("|                                                                      |");
        System.out.println("| [BACKSPACE] - Go back                                                |");
        System.out.println("| [ESC] - Quit application                                             |");
        System.out.println("└                                                                      ┘");
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
        AtomicReference<Boolean> waiting1 = new AtomicReference<>(true);
        AtomicReference<Boolean> waiting2 = new AtomicReference<>(true);
        while (waiting1.get()) {
            clearConsoleScreen();
            beachPlacesPrinter.getSeaRowPrinter().beachStructure();
            System.out.println("");
            System.out.flush();
            beachPlacesPrinter.printListOfObjects(beachPlacesManager.getAll());
            if (!(beachPlacesManager.getAll().isEmpty())) {
                for (BeachPlace bp : beachPlacesManager.getAll()) {
                    addCommand(bp.getID(), c -> beachPlacesPrinter.printFullVersion(bp));
                }
            }
            addCommand("exit", c -> waiting1.set(false));
            System.out.println("\nType " + ConsoleColors.RED + "exit" + ConsoleColors.RESET + " when you want to go back to the beach service menu.");
            System.out.flush();
            try {
                GlobalScreen.unregisterNativeHook();
                System.out.print(" > ");
                System.out.flush();
                String command = in.nextLine();
                while (waiting2.get()) {
                    processCommand(command);
                    addCommand("back", c -> waiting2.set(false));
                    addCommand("B", c -> bookBeachPlace(command));
                    System.out.println("\nType " + ConsoleColors.GREEN + "B" + ConsoleColors.RESET + " to book this beach place.\n");
                    System.out.flush();
                    System.out.println("Type " + ConsoleColors.RED + "back" + ConsoleColors.RESET + " when you want to go back to the beach places list.");
                    System.out.flush();
                    System.out.print(" > ");
                    System.out.flush();
                    String command2 = in.nextLine();
                    processCommand(command2);
                }
            } catch (NativeHookException nhe) {
                nhe.printStackTrace();
            }

        }
        commands.clear();
    }

    public void bookBeachPlace(String command) {
        String input = new String();
        while (!input.equals("STOP")) {
            clearConsoleScreen();
            System.out.println("╔═══════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                    " + ConsoleColors.YELLOW + "Beach place reservation procedure" + ConsoleColors.RESET + "                  ║");
            System.out.println("╚═══════════════════════════════════════════════════════════════════════╝");
            System.out.println("┌                                                                       ┐");
            System.out.println("| Booking period entry fields:                                          |");
            System.out.println("|                                                                       |");
            System.out.println("| [Please enter dates fields in the following format: \"" + ConsoleColors.GREEN + "dd-mm-yy hh:mm" + ConsoleColors.RESET + "\"] |");
            System.out.println("| [Or enter \"" + ConsoleColors.RED + "STOP" + ConsoleColors.RESET + "\" to abort the operation                           ] |");
            System.out.println("|                                                                       |");
            System.out.print  ("| - booking start date >                                                |");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm").withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime startDateTime = null;
            input = in.nextLine();
            while (!input.equals("STOP") && in.hasNext()) {
                try {
                    startDateTime = LocalDateTime.parse(input, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.print  ("| - booking start date >                                                |");
                    input = in.nextLine();
                }
            }
            System.out.print  ("| - booking end date >                                                  |");
            LocalDateTime endDateTime = null;
            input = in.nextLine();
            while (!input.equals("STOP") && in.hasNext()) {
                try {
                    endDateTime = LocalDateTime.parse(input, formatter);
                    break;
                } catch (DateTimeParseException dtpe) {
                    System.err.println("\nPlese enter a valid value or enter the STOP keyword.");
                    System.out.print  ("| - booking end date >                                                  |");
                    input = in.nextLine();
                }
            }
            try {
                reservationsManager.booking(BookableEntityType.BeachPlace.name(), LoginContextHolder.getCurrentAppUser(), command, startDateTime, endDateTime);
            } catch (AlreadyExistingException | IllegalStateException exceptions) {
                System.err.println(exceptions.getMessage());
                continue;
            } catch (EntityNotFoundException enfe) {
                System.err.println(enfe.getMessage());
                break;
            }
        }
        System.out.flush();
    }






    
    
}
