package com.github.Elmicass.SFJTeam_Casotto.email;

public interface EmailSender {

    /**
     * 
     * @param to
     * @param email
     */
    void send(String to, String email);

    /**
     * 
     * @param name
     * @param link
     * @return
     */
    String getEmailBody(String name, String link);

}