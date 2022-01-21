package com.github.Elmicass.SFJTeam_Casotto.services;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISeaRowRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class SeaRowServices implements ISeaRowServices {

    @Autowired
    private ISeaRowRepository seaRowRepository;

    @Override
    public SeaRow getInstance(String id) throws EntityNotFoundException {
        return seaRowRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No sea row found with the given id: " + id));
    }

    @Override
    public boolean createSeaRow(@NonNull Integer seaRowNumber, @NonNull Integer maxBPs, @NonNull Double price)
            throws AlreadyExistingException {
        SeaRow seaRow = new SeaRow(seaRowNumber, maxBPs, price);
        if (seaRowRepository.findBySeaRowNumber(seaRowNumber).equals(seaRow))
            throw new AlreadyExistingException(
                    "The sea row you are trying to create already exists, with the same row number: "
                            + seaRowNumber.toString());
        seaRowRepository.save(seaRow);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The sea row ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The sea row with ID: " + id + " does not exist");
        seaRowRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The sea row ID value is empty");
        return seaRowRepository.existsById(id);
    }
    

    
    
}