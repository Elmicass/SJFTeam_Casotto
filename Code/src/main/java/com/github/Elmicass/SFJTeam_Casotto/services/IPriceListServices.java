package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;

public interface IPriceListServices extends EntityServices<PriceList> {

    /**
     * 
     * @return
     */
    PriceList getDefault();

    /**
     * 
     * @param name
     * @param singleSunbed
     * @param smallSunshade
     * @param mediumSunshade
     * @param largeSunshade
     * @return
     * @throws AlreadyExistingException
     */
    boolean createPriceList(String name, Double singleSunbed, Double smallSunshade, Double mediumSunshade, Double largeSunshade) throws AlreadyExistingException;
    
}
