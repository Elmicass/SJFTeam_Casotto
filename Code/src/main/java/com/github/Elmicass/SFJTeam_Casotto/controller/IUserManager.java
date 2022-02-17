package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.User;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserManager extends EntityManager<User> {

    /**
     * 
     * @param email
     * @return
     */
    boolean existsByEmail(String email);

    /**
     * 
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    User getUserByEmail(String email) throws UsernameNotFoundException;

    /**
     * 
     * @param email
     * @return
     */
    org.springframework.security.core.userdetails.User loadUserByUsername(String email);

}
