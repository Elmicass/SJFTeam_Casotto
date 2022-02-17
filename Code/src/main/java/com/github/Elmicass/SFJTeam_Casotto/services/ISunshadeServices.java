package com.github.Elmicass.SFJTeam_Casotto.services;

import java.io.IOException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;
import com.google.zxing.WriterException;

/**
 * Questa interfaccia è responsabile della gestione di tutti gli ombrelloni nel sistema.
 * Sa restituire un'istanza di qualsiasi ombrellone mediante il suo ID, può crearne di nuovi o eliminarne di esistenti.
 */
public interface ISunshadeServices extends EntityServices<Sunshade> {

    /**
     * 
     * @param bp
     * @param pl
     * @return
     * @throws AlreadyExistingException
     */
    Sunshade createSunshade(SunshadeType type, BeachPlace bp, PriceList pl) throws AlreadyExistingException;

    /**
     * 
     * @param sunshade
     * @return
     * @throws AlreadyExistingException
     * @throws IOException
     * @throws WriterException
     */
    Sunshade saveSunshade(Sunshade sunshade) throws WriterException, IOException, AlreadyExistingException;



    
}
