package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.User;

/**
 * Questa interfaccia è responsabile della gestione di tutti gli User nel sistema.
 * Sa restituire un'istanza di qualsiasi User mediante la sua email, può crearne di nuovi o eliminarne di esistenti.
 */
public interface IUserServices extends EntityServices<User, String> {

    /**
     * 
     * @param name
     * @param surname
     * @param email
     * @return
     */
    boolean createUser(String name, String surname, String email);
    
}
