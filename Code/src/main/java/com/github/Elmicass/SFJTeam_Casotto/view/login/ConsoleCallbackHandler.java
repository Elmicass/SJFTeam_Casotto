package com.github.Elmicass.SFJTeam_Casotto.view.login;

import java.io.Console;
import java.io.IOException;
import java.io.*;
import java.util.*;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.TextOutputCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class ConsoleCallbackHandler implements CallbackHandler {

    // Console non funzionerà tramite IDE! Per testing del metodo tramite IDE, si faccia riferimento alla seconda versione commentata.
    @Override
    public void handle(Callback[] callbacks) throws UnsupportedCallbackException, IOException {
        Console console = System.console();
        for (Callback callback : callbacks) {
            if (callback instanceof TextOutputCallback) {
                // display the message according to the specified type
                TextOutputCallback toc = (TextOutputCallback)callback;
                switch (toc.getMessageType()) {
                   case TextOutputCallback.INFORMATION:
                      System.out.println(toc.getMessage());
                      break;
                   case TextOutputCallback.ERROR:
                      System.out.println("ERROR: " + toc.getMessage());
                      break;
                   case TextOutputCallback.WARNING:
                      System.out.println("WARNING: " + toc.getMessage());
                      break;
                   default:
                      throw new IOException("Unsupported message type: " + toc.getMessageType());
                }
            }
            else if (callback instanceof NameCallback) {
                NameCallback nameCallback = (NameCallback) callback;
                nameCallback.setName(console.readLine(nameCallback.getPrompt()));
            } else if (callback instanceof PasswordCallback) {
                PasswordCallback passwordCallback = (PasswordCallback) callback;
                passwordCallback.setPassword(console.readPassword(passwordCallback.getPrompt()));
            } else {
                throw new UnsupportedCallbackException(callback, "Unrecognized callback.");
            }
        }
    }

    /**
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof TextOutputCallback) {
                // display the message according to the specified type
                TextOutputCallback toc = (TextOutputCallback)callback;
                switch (toc.getMessageType()) {
                   case TextOutputCallback.INFORMATION:
                      System.out.println(toc.getMessage());
                      break;
                   case TextOutputCallback.ERROR:
                      System.out.println("ERROR: " + toc.getMessage());
                      break;
                   case TextOutputCallback.WARNING:
                      System.out.println("WARNING: " + toc.getMessage());
                      break;
                   default:
                      throw new IOException("Unsupported message type: " + toc.getMessageType());
                }
            } else if (callback instanceof NameCallback) {
                // prompt the user for a username
                NameCallback nc = (NameCallback)callback;
                System.err.print(nc.getPrompt());
                System.err.flush();
                nc.setName((new BufferedReader(new InputStreamReader(System.in))).readLine());
            } else if (callback instanceof PasswordCallback) {
               // prompt the user for sensitive informatio
               PasswordCallback pc = (PasswordCallback)callback;
               System.err.print(pc.getPrompt());
               System.err.flush();
               pc.setPassword((new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray());
            } else {
                throw new UnsupportedCallbackException(callback, "Unrecognized callback");
            }
        }
    }
    */

}
