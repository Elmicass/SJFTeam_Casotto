package com.github.Elmicass.SFJTeam_Casotto.controller;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.exception.EntityNotFoundException;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;
import com.github.Elmicass.SFJTeam_Casotto.services.SeaRowServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SeaRowsManager implements ISeaRowManager {

    @Autowired
    private SeaRowServices services;

    @Override
    public SeaRow getInstance(String id) throws EntityNotFoundException {
        return services.getInstance(id);
    }

    @Override
    public List<SeaRow> getAll() {
        return services.getAll();
    }

    @Override
    public boolean createSeaRow(Integer seaRowNumber, Integer maxBPs, Double price)
            throws AlreadyExistingException {
        return services.createSeaRow(seaRowNumber, maxBPs, price);
    }

    @Override
    public boolean delete(String id) {
        return services.delete(id);
    }

    @Override
    public boolean exists(String id) {
        return services.exists(id);
    }
    
}
