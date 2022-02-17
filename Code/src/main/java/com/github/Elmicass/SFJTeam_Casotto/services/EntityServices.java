package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface EntityServices<T> {

    T getInstance(Integer id) throws EntityNotFoundException;

    List<T> getAll();

    boolean delete(Integer id);

    boolean exists(Integer id);
    
}
