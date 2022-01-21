package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.registration.RegistrationRequest;

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
     * @param password
     * @return
     * @throws AlreadyExistingException
     */
    User createUser(RegistrationRequest userData) throws AlreadyExistingException;
    
}
