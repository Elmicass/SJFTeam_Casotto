package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;
import com.github.Elmicass.SFJTeam_Casotto.repository.IActivitiesRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IEquipmentsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class ActivityServices implements IActivityServices {

    @Autowired
    private IActivitiesRepository actRepository;

    @Autowired
    private IEquipmentsRepository eqRepository;

    @Autowired
    private ReservationServices reservationServices;

    @Override
    public Activity getInstance(@NonNull String id) throws EntityNotFoundException {
        return actRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No activity found with the given id: " + id));
    }

    @Override
    public boolean createActivity(@NonNull String name, @NonNull String description, @NonNull int maxEntries,
            @NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime, @NonNull Set<String> equipmentsName)
            throws AlreadyExistingException {
        Set<Equipment> equipments = activityCreationErrorsChecking(startTime, endTime, equipmentsName);
        Activity activity = new Activity(name, description, maxEntries, startTime, endTime, equipments);
        if (actRepository.findByNameAndTimeslot(name, new TimeSlot(startTime, endTime)).equals(activity))
            throw new AlreadyExistingException(
                    "The activity you are trying to create already exists, with the same name: " + name
                            + " and same timeslot: " + startTime.toString() + " / " + endTime.toString());
        actRepository.save(activity);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The activity ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The activity with ID: " + id + " does not exist");
        for (Reservation res : getInstance(id).getReservations()) {
            reservationServices.cancelBooking(res.getID());
        }
        actRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The activity ID value is empty");
        return actRepository.existsById(id);
    }

    @Override
    public boolean booking(String activityID, Reservation reservation) throws IllegalStateException, AlreadyExistingException {
        Activity activity = getInstance(activityID);
        if (activity.addReservation(reservation)) {
            actRepository.save(activity);
            return true;
        } else return false;
    }

    @Override
    public boolean cancelBooking(Reservation toCancel, String activityID) {
        Activity act = getInstance(activityID);
        if (act.removeReservation(toCancel)) {
            return !actRepository.save(act).getReservations().contains(toCancel);
        } else return false;
    }

    public Set<Equipment> activityCreationErrorsChecking(LocalDateTime start, LocalDateTime stop,
            Set<String> equipmentsNames) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (start.isBefore(currentTime) || stop.isBefore(currentTime))
            throw new IllegalArgumentException(
                    "You are trying to create an activity with starting or ending time before than the current time");
        Set<Equipment> equipments = new HashSet<>();
        if (equipmentsNames.isEmpty())
            return equipments;
        else {
            for (String equipmentName : equipmentsNames) {
                Equipment equipment = eqRepository.findByName(equipmentName);
                if (!(equipment.equals(null)))
                    equipments.add(equipment);
            }
            if (equipments.isEmpty())
                throw new IllegalArgumentException("You are trying to create an activity with nonexistent equipments");
            else
                return equipments;
        }
    }

}
