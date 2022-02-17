package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.repository.IPriceListRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class PriceListServices implements IPriceListServices {

    @Autowired
    private IPriceListRepository priceListRepository;

    @Override
    public PriceList getInstance(Integer id) throws EntityNotFoundException {
        return priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No price list found with the given id: " + id));
    }

    @Override
    public List<PriceList> getAll() {
        return priceListRepository.findAll();
    }

    @Override
    public boolean createPriceList(@NonNull String name, @NonNull Double sunbedHourly, @NonNull Double smallSunSHourly,
            @NonNull Double medSunSHourly, @NonNull Double largeSunSHourly) throws AlreadyExistingException {
        PriceList priceList = new PriceList(name, sunbedHourly, smallSunSHourly, medSunSHourly, largeSunSHourly);
        if (priceListRepository.findByName(name).equals(priceList))
            throw new AlreadyExistingException(
                    "The price list you are trying to create already exists, with the same name: "
                            + name);
        priceListRepository.save(priceList);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The sea row ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The sea row with ID: " + id + " does not exist");
        priceListRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The price list ID value is empty");
        return priceListRepository.existsById(id);
    }

}
