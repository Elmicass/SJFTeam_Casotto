package com.github.Elmicass.SFJTeam_Casotto.services;

import java.io.IOException;

import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.google.zxing.WriterException;


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
     * @throws ReachedLimitOfObjects
     * @throws IOException
     * @throws WriterException
     * @throws IllegalStateException
     * @throws IllegalArgumentException
     */
    boolean createBeachPlace(int seaRowNumber, int position, String priceListName, String sunshadeType, int sunbedsNumber) throws IllegalArgumentException, IllegalStateException, WriterException, IOException, ReachedLimitOfObjects;
  
}
