package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;

public interface ISunshadeManager extends EntityManager<Sunshade, String> {

    boolean createNewSunshade(/* QrCode qrcode,*/ SunshadeType type);
    
}
