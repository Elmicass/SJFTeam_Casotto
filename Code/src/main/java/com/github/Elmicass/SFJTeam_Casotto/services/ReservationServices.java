package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;

import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation.EntityType;
import com.github.Elmicass.SFJTeam_Casotto.repository.IReservationsRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;

public class ReservationServices implements IReservationServices {

    @Autowired
    private IReservationsRepository resRepository;

    @Override
    public Reservation getInstance(@NonNull String id) throws EntityNotFoundException {
        return resRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No reservations found with the given id: " + id));
    }

    @Override
    public Reservation createReservation(@NonNull EntityType type, @NonNull String user, @NonNull String entityID,
            @NonNull LocalDateTime start, @NonNull LocalDateTime end,
            @NonNull Object object) throws AlreadyExistingException {
        Reservation reservation = new Reservation(type, user, entityID, start, end, object);
        if (resRepository.findByEntityIDAndTimeslotAndUserEmail(entityID, new TimeSlot(start, end), user)
                .equals(reservation))
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

    public boolean booking(@NonNull String entityType, @NonNull String user, @NonNull String entityID,
            @NonNull LocalDateTime start, @NonNull LocalDateTime end) throws EntityNotFoundException, AlreadyExistingException {
        EntityType type = EntityType.valueOf(entityType);
        switch (type) {
            case BeachPlace:
                BeachPlaceServices bpServices = new BeachPlaceServices();
                Reservation bpRes = createReservation(type, user, entityID, start, end, bpServices.getInstance(entityID));
                if (bpServices.booking(entityID, bpRes)) {
                    resRepository.save(bpRes);
                    return true;
                }
                break;
            case Activity:
                ActivityServices actServices = new ActivityServices();
                Reservation actRes = createReservation(type, user, entityID, start, end, actServices.getInstance(entityID));
                if (actServices.booking(entityID, actRes)) {
                    resRepository.save(actRes);
                    return true;
                }
                break;
            case JobOffer:
                JobOfferServices joServices = new JobOfferServices();
                Reservation joRes = createReservation(type, user, entityID, start, end, joServices.getInstance(entityID));
                if (joServices.application(entityID, joRes)) {
                    resRepository.save(joRes);
                    return true;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + EntityType.values() + ".");
        }
        return false;
    }

    public boolean cancelBooking(@NonNull String reservationID) {
        Reservation toCancel = getInstance(reservationID);
        EntityType type = toCancel.getType();
        switch (type) {
            case BeachPlace:
                BeachPlaceServices bpServices = new BeachPlaceServices();
                if (bpServices.cancelBooking(toCancel, toCancel.getEntityID())) {
                    resRepository.deleteById(reservationID);
                    return !exists(reservationID);
                }
                break;
            case Activity:
                ActivityServices actServices = new ActivityServices();
                if (actServices.cancelBooking(toCancel, toCancel.getEntityID())) {
                    resRepository.deleteById(reservationID);
                    return !exists(reservationID);
                }
                break;
            case JobOffer:
                JobOfferServices joServices = new JobOfferServices();
                if (joServices.cancelBooking(toCancel, toCancel.getEntityID())) {
                    resRepository.deleteById(reservationID);
                    return !exists(reservationID);
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + EntityType.values() + ".");
        }
        return false;
    }

}
