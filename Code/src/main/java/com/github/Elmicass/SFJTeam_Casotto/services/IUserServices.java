package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.registration.RegistrationRequest;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Questa interfaccia è responsabile della gestione di tutti gli User nel sistema.
 * Sa restituire un'istanza di qualsiasi User mediante la sua email, può crearne di nuovi o eliminarne di esistenti.
 */
public interface IUserServices extends EntityServices<User> {

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

    /**
     * 
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    User getUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * 
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 
     * @param email
     * @return
     */
    boolean existsByEmail(String email);
    
}
