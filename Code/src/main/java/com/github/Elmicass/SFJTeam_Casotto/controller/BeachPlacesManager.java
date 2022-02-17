package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.io.IOException;
import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.services.IBeachPlaceServices;
import com.google.zxing.WriterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BeachPlacesManager implements IBeachPlaceManager {

    @Autowired
    private IBeachPlaceServices services;

    @Override
    public BeachPlace getInstance(Integer id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public List<BeachPlace> getAll() {
        return services.getAll();
    }

    @Override
    public boolean createBeachPlace(int seaRowNumber, int position, String priceListName,
            String sunshadeType, int sunbedsNumber) throws IllegalArgumentException,
            IllegalStateException, WriterException, IOException, ReachedLimitOfObjects, AlreadyExistingException {
        return services.createBeachPlace(seaRowNumber, position, priceListName, sunshadeType, sunbedsNumber);
    }

    @Override
    public boolean delete(Integer id) {
        return services.delete(id);
    }

    @Override
    public boolean exists(Integer id) {
        return services.exists(id);
    }

}
