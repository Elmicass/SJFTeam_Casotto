package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;

public interface ISeaRowManager extends EntityManager<SeaRow> {

    boolean createSeaRow(Integer seaRowNumber, Integer maxBPs, Double price)
            throws AlreadyExistingException;

}
