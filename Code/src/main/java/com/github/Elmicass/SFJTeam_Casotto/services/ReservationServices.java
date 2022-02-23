package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;
import com.github.Elmicass.SFJTeam_Casotto.repository.IReservationsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class ReservationServices implements IReservationServices {

    @Autowired
    private IReservationsRepository resRepository;

    @Autowired
    private IBeachPlaceServices bpServices;

    @Autowired
    private IActivityServices actServices;

    @Autowired
    private IJobOfferServices joServices;

    @Override
    public Reservation getInstance(@NonNull Integer id) throws EntityNotFoundException {
        return resRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No reservations found with the given id: " + id));
    }

    @Override
    public List<Reservation> getAll() {
        return resRepository.findAll();
    }

    @Override
    public List<Reservation> getByType(BookableEntityType type) {
        return resRepository.findByType(type);
    }

    @Override
    public Reservation save(Reservation res) {
        return resRepository.save(res);
    }

    @Override
    public Reservation createReservation(@NonNull BookableEntityType type, @NonNull User user,
            @NonNull LocalDateTime start, @NonNull LocalDateTime end,
            @NonNull IEntity object) throws AlreadyExistingException {
        if (reservationCreationErrorsChecking(start, end)) {
            Reservation reservation = new Reservation(type, user, start, end, object);
            if (resRepository.findByUserAndTimeslot(user, start, end).get().contains(reservation))
                throw new AlreadyExistingException("You are already booked to this: " + type);
            return reservation;
        } else throw new IllegalArgumentException(
                    "You are trying to create an activity with starting or ending time before than the current time");
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The reservation ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The reservation with ID: " + id + " does not exist");
        resRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The reservation ID value is empty");
        return resRepository.existsById(id);
    }

    public boolean reservationCreationErrorsChecking(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (startTime.isBefore(currentTime) || endTime.isBefore(currentTime))
            return false;
        return true;
    }

    public boolean booking(@NonNull String entityType, @NonNull User user,
            LocalDateTime inputStart, LocalDateTime inputEnd, Integer entityID)
            throws EntityNotFoundException, AlreadyExistingException, IllegalStateException {
        BookableEntityType type = BookableEntityType.valueOf(entityType);
        switch (type) {
            case BeachPlace:
                Reservation bpRes = createReservation(type, user, inputStart, inputEnd, bpServices.getInstance(entityID));
                if (bpServices.booking(bpRes.getEntityID(), bpRes)) {
                    save(bpRes);
                    bpServices.save(bpServices.getInstance(entityID));
                    return true;
                }
                break;
            case Activity:
                LocalDateTime actStart = actServices.getInstance(entityID).getTimeSlot().getStart();
                LocalDateTime actEnd = actServices.getInstance(entityID).getTimeSlot().getStop();
                Reservation actRes = createReservation(type, user, actStart, actEnd, actServices.getInstance(entityID));
                if (actServices.booking(actRes.getEntityID(), actRes)) {
                    save(actRes);
                    actServices.save(actServices.getInstance(entityID));
                    return true;
                }
                break;
            case JobOffer:
                LocalDateTime joStart = actServices.getInstance(entityID).getTimeSlot().getStart();
                LocalDateTime joEnd = actServices.getInstance(entityID).getTimeSlot().getStop();
                Reservation joRes = createReservation(type, user, joStart, joEnd, joServices.getInstance(entityID));
                if (joServices.application(joRes.getEntityID(), joRes)) {
                    save(joRes);
                    joServices.save(joServices.getInstance(entityID));
                    return true;
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "The entity type you are trying to book for is not one of: " + BookableEntityType.values() + ".");
        }
        return false;
    }

    public boolean cancelBooking(@NonNull Integer reservationID) {
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
                if (joServices.cancelApplication(toCancel, toCancel.getEntityID())) {
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
