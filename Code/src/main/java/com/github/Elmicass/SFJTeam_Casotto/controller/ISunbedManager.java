package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;

public interface ISunbedManager extends EntityManager<Sunbed, String>{

    boolean createNewSunbed();
    
}
