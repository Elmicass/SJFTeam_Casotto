package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;

public interface IPriceListManager extends EntityManager<PriceList, String> {

    boolean createPriceList(String name, Double sunbedHourly, Double smallSunSHourly, Double medSunSHourly, Double largeSunSHourly) throws AlreadyExistingException;
    
}
