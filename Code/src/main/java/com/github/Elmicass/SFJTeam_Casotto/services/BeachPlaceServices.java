package com.github.Elmicass.SFJTeam_Casotto.services;

import java.io.IOException;
import java.util.EnumSet;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.ReachedLimitOfObjects;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade.SunshadeType;
import com.github.Elmicass.SFJTeam_Casotto.repository.IBeachPlacesRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IPriceListRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.ISeaRowRepository;
import com.google.zxing.WriterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class BeachPlaceServices implements IBeachPlaceServices {

    @Autowired
    private IBeachPlacesRepository bpRepository;

    @Autowired
    private ISeaRowRepository srRepository;

    @Autowired
    private IPriceListRepository plRepository;

    @Override
    public BeachPlace getInstance(String id) throws EntityNotFoundException {
        return bpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No beach places found with the given id: " + id));
    }

    @Override
    public boolean createBeachPlace(@NonNull int seaRowNumber, @NonNull int position, @NonNull String priceListName,
            @NonNull String sunshadeType, @NonNull int sunbedsNumber) throws IllegalArgumentException,
            IllegalStateException, WriterException, IOException, ReachedLimitOfObjects {
        PriceList priceList = beachPlaceCreationErrorsChecking(seaRowNumber, priceListName, sunshadeType,
                sunbedsNumber);
        BeachPlace beachPlace = new BeachPlace(srRepository.findBySeaRowNumber(seaRowNumber), position, priceList,
                sunshadeType, sunbedsNumber);
        SunshadeServices sunshadeServices = new SunshadeServices();
        sunshadeServices.saveSunshade(beachPlace.getSunshade());
        SunbedServices sunbedServices = new SunbedServices();
        for (Sunbed sunbed : beachPlace.getSunbeds()) {
            sunbedServices.saveSunbed(sunbed);
        }
        bpRepository.save(beachPlace);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank()) throw new IllegalArgumentException("The beach place ID is empty");
        if (!(exists(id))) throw new EntityNotFoundException("The beach place with ID: " + id + " does not exist");
        bpRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank()) throw new IllegalArgumentException("The beach place ID value is empty");
        return bpRepository.existsById(id);
    }

    public PriceList beachPlaceCreationErrorsChecking(int seaRowNumber, String priceListName, String sunshadeType,
            int sunbedsNumber) {
        if (!(srRepository.existBySeaRowNumber(seaRowNumber)))
            throw new IllegalArgumentException(
                    "The sea row number you entered does not correspond to any existing sea row.");
        if (!(EnumSet.allOf(SunshadeType.class).contains(SunshadeType.valueOf(sunshadeType))))
            throw new IllegalArgumentException(
                    "The sunshade type you entered is not valid. Allowed values are: " + SunshadeType.values() + ".");
        if (!(sunbedsNumber > 0))
            throw new IllegalArgumentException(
                    "You are trying to create a beach place with 0 sunbeds. Insert a value greater than zero.");
        if (!(plRepository.existByName(priceListName)))
            return plRepository.findByName("DEFAULT");
        else
            return plRepository.findByName(priceListName);
    }

    public boolean booking(String beachPlaceID, Reservation reservation) {
        BeachPlace bp = getInstance(beachPlaceID);
        if (bp.addReservation(reservation)) {
            return !bpRepository.save(bp).getReservations().contains(reservation);
        } else return false;
    }

    public boolean cancelBooking(Reservation toCancel, String beachPlaceID) {
        BeachPlace bp = getInstance(beachPlaceID);
        if (bp.removeReservation(toCancel)) {
            return !bpRepository.save(bp).getReservations().contains(toCancel);
        } else return false;
    }


    

}
