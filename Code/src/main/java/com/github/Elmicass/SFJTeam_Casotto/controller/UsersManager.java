package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.services.IUserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

@Controller
public class UsersManager implements IUserManager {

    @Autowired
    private IUserServices services;

    @Override
    public User getInstance(Integer id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return services.getUserByUsername(email);
    }

    @Override
    public List<User> getAll() {
        return services.getAll();
    }

    @Override
    public User save(User object) {
        return services.save(object);
    }
    
    @Override
    public boolean delete(Integer id) {
        return services.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return services.exists(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return services.existsByEmail(email);
    }
    
    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) {
        return services.loadUserByUsername(username);
    }
    
}
