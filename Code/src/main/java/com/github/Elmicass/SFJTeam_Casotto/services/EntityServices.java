package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

public interface EntityServices<T,I extends CharSequence> {

    T getInstance(I id) throws EntityNotFoundException;

    boolean delete(I id);

    boolean exists(I id);
    
}
