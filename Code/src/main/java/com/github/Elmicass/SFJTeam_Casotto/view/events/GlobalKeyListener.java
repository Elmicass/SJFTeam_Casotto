package com.github.Elmicass.SFJTeam_Casotto.view.events;

import java.util.Map;
import java.util.function.Consumer;

import com.github.Elmicass.SFJTeam_Casotto.view.IConsoleView;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {
    
    private Map<String, Consumer<? super IConsoleView>> commands;

    private IConsoleView consoleView;

    public GlobalKeyListener(Map<String, Consumer<? super IConsoleView>> commands, IConsoleView consoleView) {
        this.commands = commands;
        this.consoleView = consoleView;
    }

    public GlobalKeyListener() {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        if (this.commands.containsKey(NativeKeyEvent.getKeyText(e.getKeyCode()))) {
            try {
                GlobalScreen.unregisterNativeHook();
                this.consoleView.processCommand(NativeKeyEvent.getKeyText(e.getKeyCode()));
            } catch (NativeHookException nhe) {
                nhe.printStackTrace();
            }
        } else {
            System.err.println("Unknown command: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        }
    }

    public int keyPressedCode(NativeKeyEvent e) {
        return e.getKeyCode();
    }

}