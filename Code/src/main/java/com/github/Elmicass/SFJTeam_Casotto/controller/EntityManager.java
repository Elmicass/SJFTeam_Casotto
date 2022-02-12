package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;

public interface EntityManager<T, I> {

    T getInstance(I id) throws EntityNotFoundException;

    List<T> getAll();

    boolean delete(I id);

    boolean exists(I id);
    
}
