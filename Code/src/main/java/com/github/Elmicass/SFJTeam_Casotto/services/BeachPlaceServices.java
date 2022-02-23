package com.github.Elmicass.SFJTeam_Casotto.services;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class BeachPlaceServices implements IBeachPlaceServices {

    @Autowired
    private IBeachPlacesRepository bpRepository;

    @Autowired
    private ISeaRowRepository srRepository;

    @Autowired
    private IPriceListRepository plRepository;

    @Autowired
    private ISunshadeServices sunshadeServices;

    @Autowired
    private ISunbedServices sunbedServices;

    @Autowired
    @Lazy
    private IReservationServices reservationServices;

    @Override
    public BeachPlace getInstance(Integer id) throws EntityNotFoundException {
        return bpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No beach places found with the given id: " + id));
    }

    @Override
    public List<BeachPlace> getAll() {
        return bpRepository.findAll();
    }

    @Override
    public BeachPlace save(BeachPlace bp) {
        return bpRepository.save(bp);
    }

    @Override
    public boolean createBeachPlace(@NonNull Integer seaRowNumber, @NonNull Integer position, @NonNull String priceListName,
            @NonNull String sunshadeType, @NonNull Integer sunbedsNumber) throws IllegalArgumentException,
            IllegalStateException, WriterException, IOException, ReachedLimitOfObjects, AlreadyExistingException {
        PriceList priceList = beachPlaceCreationErrorsChecking(seaRowNumber, priceListName, sunshadeType,
                sunbedsNumber);
        BeachPlace beachPlace = new BeachPlace(srRepository.findBySeaRowNumber(seaRowNumber).get(), position, priceList,
                sunshadeType, sunbedsNumber);
        save(beachPlace).setSunshade(sunshadeServices.generateQrCode(sunshadeServices.createSunshade(SunshadeType.valueOf(sunshadeType), beachPlace, priceList)));
        for (Sunbed sunbed : beachPlace.getSunbeds()) {
            sunbedServices.save(sunbed);
        }
        save(beachPlace);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank()) throw new IllegalArgumentException("The beach place ID is empty");
        if (!(exists(id))) throw new EntityNotFoundException("The beach place with ID: " + id + " does not exist");
        for (Reservation res : getInstance(id).getReservations()) {
            reservationServices.cancelBooking(res.getID());
        }
        sunshadeServices.delete(getInstance(id).getSunshade().getID());
        for (Sunbed sunbed : getInstance(id).getSunbeds()) {
            sunbedServices.delete(sunbed.getID());
        }
        getInstance(id).getSeaRow().removeBeachPlace(getInstance(id));
        bpRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank()) throw new IllegalArgumentException("The beach place ID value is empty");
        return bpRepository.existsById(id);
    }

    public PriceList beachPlaceCreationErrorsChecking(int seaRowNumber, String priceListName, String sunshadeType,
            int sunbedsNumber) throws IllegalArgumentException {
        if (!(srRepository.existsBySeaRowNumber(seaRowNumber)))
            throw new IllegalArgumentException(
                    "The sea row number you entered does not correspond to any existing sea row.");
        if (!(EnumSet.allOf(SunshadeType.class).contains(SunshadeType.valueOf(sunshadeType))))
            throw new IllegalArgumentException(
                    "The sunshade type you entered is not valid. Allowed values are: " + SunshadeType.values() + ".");
        if (!(sunbedsNumber > 0))
            throw new IllegalArgumentException(
                    "You are trying to create a beach place with 0 sunbeds. Insert a value greater than zero.");
        if (!(plRepository.existsByName(priceListName)))
            return plRepository.findByName("DEFAULT").get();
        else
            return plRepository.findByName(priceListName).get();
    }

    public boolean booking(Integer beachPlaceID, Reservation reservation) throws IllegalStateException {
        BeachPlace bp = getInstance(beachPlaceID);
        if (bp.addReservation(reservation)) {
            return true;
        } else return false;
    }

    public boolean cancelBooking(Reservation toCancel, Integer beachPlaceID) {
        BeachPlace bp = getInstance(beachPlaceID);
        if (bp.removeReservation(toCancel)) {
            return !bpRepository.save(bp).getReservations().contains(toCancel);
        } else return false;
    }


    

}
