package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Account;

/**
 * Questa interfaccia è responsabile della gestione di tutti gli account nel sistema.
 * Sa restituire un'istanza di qualsiasi account mediante la sua email, può crearne di nuovi o eliminarne di esistenti.
 */
public interface IAccountServices extends EntityServices<Account, String> {

    /**
     * 
     * @param name
     * @param surname
     * @param email
     * @return
     */
    boolean createAccount(String name, String surname, String email);
    
}
