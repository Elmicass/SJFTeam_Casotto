package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;

/**
 * Questa interfaccia è responsabile della gestione di tutti gli ombrelloni nel sistema.
 * Sa restituire un'istanza di qualsiasi ombrellone mediante il suo ID, può crearne di nuovi o eliminarne di esistenti.
 */
public interface ISunshadeServices extends EntityServices<Sunshade, String> {

    /**
     * 
     * @param sunshade
     * @return
     */
    boolean saveSunshade(Sunshade sunshade);



    
}
