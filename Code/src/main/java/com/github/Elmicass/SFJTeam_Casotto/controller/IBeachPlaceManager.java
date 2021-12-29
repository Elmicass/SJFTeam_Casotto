package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;

public interface IBeachPlaceManager extends EntityManager<BeachPlace, String> {

    boolean createNewBeachPlace(int seaRowNumber, Sunshade sunshade, Sunbed[] sunbed);

    
}
