package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.repository.IReservationsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class ReservationServices implements IReservationServices {

    @Autowired
    private IReservationsRepository resRepository;

    @Autowired
    private BeachPlaceServices bpServices;

    @Autowired
    private ActivityServices actServices;

    @Autowired
    private JobOfferServices joServices;

    @Override
    public Reservation getInstance(@NonNull String id) throws EntityNotFoundException {
        return resRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No reservations found with the given id: " + id));
    }

    @Override
    public List<Reservation> getAll() {
        return resRepository.findAll();
    }

    @Override
    public Reservation createReservation(@NonNull BookableEntityType type, @NonNull User user,
            @NonNull LocalDateTime start, @NonNull LocalDateTime end,
            @NonNull IEntity object) throws AlreadyExistingException {
        Reservation reservation = new Reservation(type, user, start, end, object);
        if (resRepository.findByTimeslotAndUserEmail(new TimeSlot(start, end), user).contains(reservation))
            throw new AlreadyExistingException("You are already booked to this: " + type);
        return resRepository.save(reservation);
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The reservation ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The reservation with ID: " + id + " does not exist");
        resRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The reservation ID value is empty");
        return resRepository.existsById(id);
    }

    public boolean reservationCreationErrorsChecking(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (startTime.isBefore(currentTime) || endTime.isBefore(currentTime))
            throw new IllegalArgumentException(
                    "You are trying to create an activity with starting or ending time before than the current time");
        return true;
    }

    public boolean booking(@NonNull String entityType, @NonNull User user,
            @NonNull LocalDateTime start, @NonNull LocalDateTime end, String entityID)
            throws EntityNotFoundException, AlreadyExistingException, IllegalStateException {
        BookableEntityType type = BookableEntityType.valueOf(entityType);
        switch (type) {
            case BeachPlace:
                Reservation bpRes = createReservation(type, user, start, end, bpServices.getInstance(entityID));
                if (bpServices.booking(bpRes.getEntityID(), bpRes)) {
                    resRepository.save(bpRes);
                    return true;
                }
                break;
            case Activity:
                Reservation actRes = createReservation(type, user, start, end, actServices.getInstance(entityID));
                if (actServices.booking(actRes.getEntityID(), actRes)) {
                    resRepository.save(actRes);
                    return true;
                }
                break;
            case JobOffer:
                Reservation joRes = createReservation(type, user, start, end, joServices.getInstance(entityID));
                if (joServices.application(joRes.getEntityID(), joRes)) {
                    resRepository.save(joRes);
                    return true;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + BookableEntityType.values() + ".");
        }
        return false;
    }

    public boolean cancelBooking(@NonNull String reservationID) {
        Reservation toCancel = getInstance(reservationID);
        BookableEntityType type = toCancel.getType();
        switch (type) {
            case BeachPlace:
                if (bpServices.cancelBooking(toCancel, toCancel.getEntityID())) {
                    resRepository.deleteById(reservationID);
                    return !exists(reservationID);
                }
                break;
            case Activity:
                if (actServices.cancelBooking(toCancel, toCancel.getEntityID())) {
                    resRepository.deleteById(reservationID);
                    return !exists(reservationID);
                }
                break;
            case JobOffer:
                if (joServices.cancelBooking(toCancel, toCancel.getEntityID())) {
                    resRepository.deleteById(reservationID);
                    return !exists(reservationID);
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + BookableEntityType.values() + ".");
        }
        return false;
    }

}
