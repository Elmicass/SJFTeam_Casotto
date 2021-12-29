package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.Account;
import com.github.Elmicass.SFJTeam_Casotto.repository.IAccountsRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class AccountServices implements IAccountServices {

    @Autowired
    private IAccountsRepository repository;

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
    public boolean createAccount(String name, String surname, String email) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
