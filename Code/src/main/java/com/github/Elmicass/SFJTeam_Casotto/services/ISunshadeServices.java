package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;

/**
 * Questa interfaccia è responsabile della gestione di tutti gli ombrelloni nel sistema.
 * Sa restituire un'istanza di qualsiasi ombrellone mediante il suo ID, può crearne di nuovi o eliminarne di esistenti.
 */
public interface ISunshadeServices extends EntityServices<Sunshade, String> {

    /**
     * 
     * @param type
     * @return
     */
    boolean createSunshade(/* QrCode : qrcode ,*/ SunshadeType type);



    
}
