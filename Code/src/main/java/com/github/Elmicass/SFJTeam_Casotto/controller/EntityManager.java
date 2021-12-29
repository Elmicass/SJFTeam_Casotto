package com.github.Elmicass.SFJTeam_Casotto.controller;

import javax.persistence.EntityNotFoundException;

public interface EntityManager<T, I> {

    T getInstance(I id) throws EntityNotFoundException;

    boolean delete(I id);

    boolean exists(I id);
    
}
