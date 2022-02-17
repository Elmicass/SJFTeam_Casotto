package com.github.Elmicass.SFJTeam_Casotto.services;

import java.io.IOException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.QrCode;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.google.zxing.WriterException;

public interface IQrCodeServices extends EntityServices<QrCode> {

    /**
     * 
     * @param sunshade
     * @return
     * @throws AlreadyExistingException
     */
    QrCode createQrCode(Sunshade sunshade) throws WriterException, IOException, AlreadyExistingException;

    /**
     * 
     * @param qrCode
     * @return
     */
    QrCode saveQrCode(QrCode qrCode);
    
}
