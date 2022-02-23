package com.github.Elmicass.SFJTeam_Casotto.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;
import com.github.Elmicass.SFJTeam_Casotto.repository.IPriceListRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunbedsRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISunshadesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class PriceListServices implements IPriceListServices {

    @Autowired
    private IPriceListRepository priceListRepository;

    @Autowired
    private ISunshadeServices sunshadeServices;

    @Autowired
    private ISunbedServices sunbedServices;

    @Autowired
    private ISunshadesRepository sunshadesRepository;

    @Autowired
    private ISunbedsRepository sunbedsRepository;

    @Override
    public PriceList getInstance(Integer id) throws EntityNotFoundException {
        return priceListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No price list found with the given id: " + id));
    }

    @Override
    public PriceList getDefault() {
        return priceListRepository.findByName("DEFAULT").get();
    }

    @Override
    public List<PriceList> getAll() {
        return priceListRepository.findAll();
    }

    @Override
    public PriceList save(PriceList pl) {
        return priceListRepository.save(pl);
    }

    @Override
    public boolean createPriceList(@NonNull String name, @NonNull Double sunbedHourly, @NonNull Double smallSunSHourly,
            @NonNull Double medSunSHourly, @NonNull Double largeSunSHourly) throws AlreadyExistingException {
        PriceList priceList = new PriceList(name, sunbedHourly, smallSunSHourly, medSunSHourly, largeSunSHourly);
        if (priceListRepository.findByName(name).isPresent())
            throw new AlreadyExistingException(
                    "The price list you are trying to create already exists, with the same name: "
                            + name);
        save(priceList);
        return true;
    }

    @Override
    public boolean delete(Integer id) throws IllegalStateException, EntityNotFoundException {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The sea row ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The sea row with ID: " + id + " does not exist");
        if (getInstance(id).getName().equals("DEFAULT"))
            throw new IllegalStateException("It is not possible to delete the default price list");
        for (Sunshade sunshade : sunshadesRepository.findByPriceList(getInstance(id))) {
            sunshade.setPriceList(getDefault());
            sunshadeServices.save(sunshade);
        }
        for (Sunbed sunbed : sunbedsRepository.findByPriceList(getInstance(id))) {
            sunbed.setPriceList(getDefault());
            sunbedServices.save(sunbed);
        }
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
