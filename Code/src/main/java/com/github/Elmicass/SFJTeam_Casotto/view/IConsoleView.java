package com.github.Elmicass.SFJTeam_Casotto.view;

public interface IConsoleView {

    void clearConsoleScreen();

    void open();

    void close();

    void processCommand(String command);
    
}
