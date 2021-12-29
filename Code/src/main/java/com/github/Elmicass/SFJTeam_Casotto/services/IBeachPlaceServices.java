package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;

/**
 * Questa interfaccia è responsabile della gestione di tutti i posti spiaggia nel sistema.
 * Sa restituire un'istanza di qualsiasi posto spiaggia mediante il suo ID, può crearne di nuovi o eliminarne di esistenti.
 */
public interface IBeachPlaceServices extends EntityServices<BeachPlace, String> {

    /**
     * 
     * @param seaRowNumber
     * @param sunshade
     * @param sunbeds
     * @return
     */
    boolean createBeachPlace(int seaRowNumber, Sunshade sunshade, Sunbed[] sunbeds);
  
}
