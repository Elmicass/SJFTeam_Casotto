package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.repository.IUsersRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServices implements IUserServices {

    @Autowired
    private IUsersRepository repository;

    @Override
    public User getInstance(String id) throws EntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No users found with the given id: " + id));
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
    public boolean createUser(String name, String surname, String email) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
