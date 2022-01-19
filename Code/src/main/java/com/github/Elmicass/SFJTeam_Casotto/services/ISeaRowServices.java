package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;

public interface ISeaRowServices extends EntityServices<SeaRow, String> {

    boolean createSeaRow(Integer seaRowNumber, Integer maxBPs, Double price) throws AlreadyExistingException;
    
}
