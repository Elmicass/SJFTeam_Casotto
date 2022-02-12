package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.io.IOException;

import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.google.zxing.WriterException;

public interface IBeachPlaceManager extends EntityManager<BeachPlace, String> {

    boolean createBeachPlace(int seaRowNumber, int position, String priceListName,
            String sunshadeType, int sunbedsNumber) throws IllegalArgumentException,
            IllegalStateException, WriterException, IOException, ReachedLimitOfObjects;

}
