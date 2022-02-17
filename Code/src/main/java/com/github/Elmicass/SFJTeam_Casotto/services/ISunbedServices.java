package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;

/**
 * Questa interfaccia è responsabile della gestione di tutte le sdraio/lettini nel sistema.
 * Sa restituire un'istanza di qualsiasi sdraio mediante il suo ID, può crearne di nuove o eliminarne di esistenti.
 */
public interface ISunbedServices extends EntityServices<Sunbed> {

    /**
     * 
     * @param sunbed
     * @return
     */
    boolean saveSunbed (Sunbed sunbed);
    
    
}
