package com.github.Elmicass.SFJTeam_Casotto.controller;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Account;
import com.github.Elmicass.SFJTeam_Casotto.services.IAccountServices;

import org.springframework.beans.factory.annotation.Autowired;

public class AccountsManager implements IAccountManager {

    @Autowired
    private IAccountServices services;

    @Override
    public Account getInstance(String id) throws EntityNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean exists(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean createNewAccount(String name, String surname, String email) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
}
