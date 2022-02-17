package com.github.Elmicass.SFJTeam_Casotto.view;

import java.io.IOException;

public interface IConsoleView {

    void clearConsoleScreen();

    void open() throws IOException;

    void close() throws IOException;

    void processCommand(String command);
    
}
