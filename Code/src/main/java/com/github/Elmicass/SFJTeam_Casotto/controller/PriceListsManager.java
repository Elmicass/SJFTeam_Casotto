package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.services.PriceListServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PriceListsManager implements IPriceListManager {

    @Autowired
    private PriceListServices services;

    @Override
    public PriceList getInstance(Integer id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public List<PriceList> getAll() {
        return services.getAll();
    }

    @Override
    public boolean createPriceList(String name, Double sunbedHourly, Double smallSunSHourly, Double medSunSHourly,
            Double largeSunSHourly) throws AlreadyExistingException {
        return services.createPriceList(name, sunbedHourly, smallSunSHourly, medSunSHourly, largeSunSHourly);
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
