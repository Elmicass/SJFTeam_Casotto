package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;

public interface EntityManager<T> {

    T getInstance(Integer id) throws EntityNotFoundException;

    List<T> getAll();

    boolean delete(Integer id) throws EntityNotFoundException, IllegalArgumentException;

    boolean exists(Integer id);
    
}
