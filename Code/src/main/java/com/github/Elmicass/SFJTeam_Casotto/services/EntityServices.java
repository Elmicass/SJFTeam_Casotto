package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

public interface EntityServices<T,I extends CharSequence> {

    T getInstance(I id) throws EntityNotFoundException;

    List<T> getAll();

    boolean delete(I id);

    boolean exists(I id);
    
}
