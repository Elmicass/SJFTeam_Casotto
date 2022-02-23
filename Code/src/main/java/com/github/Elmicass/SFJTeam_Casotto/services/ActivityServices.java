package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.repository.IActivitiesRepository;
import com.github.Elmicass.SFJTeam_Casotto.repository.IEquipmentsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class ActivityServices implements IActivityServices {

    @Autowired
    private IActivitiesRepository actRepository;

    @Autowired
    private IEquipmentsRepository eqRepository;

    @Autowired
    @Lazy
    private ReservationServices reservationServices;

    @Override
    public Activity getInstance(@NonNull Integer id) throws EntityNotFoundException {
        return actRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No activity found with the given id: " + id));
    }

    @Override
    public List<Activity> getAll() {
        return actRepository.findAll();
    }

    @Override
    public Activity save(Activity activity) {
        return actRepository.save(activity);
    }


    @Override
    public boolean createActivity(@NonNull String name, @NonNull String description, @NonNull Integer maxEntries,
            @NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime, @NonNull String[] equipmentsName)
            throws AlreadyExistingException {
        Set<Equipment> equipments = activityCreationErrorsChecking(startTime, endTime, equipmentsName);
        Activity activity = new Activity(name, description, maxEntries, startTime, endTime, equipments);
        if (actRepository.findByNameAndTimeslot(name, startTime, endTime).isPresent())
            throw new AlreadyExistingException(
                    "The activity you are trying to create already exists, with the same name: " + name
                            + " and same timeslot: " + startTime.toString() + " / " + endTime.toString());
        save(activity);
        return true;
    }

    @Override
    public boolean delete(Integer id) throws EntityNotFoundException, IllegalArgumentException {
        if (!(exists(id)))
            throw new EntityNotFoundException("The activity with ID: " + id + " does not exist");
        for (Reservation res : getInstance(id).getReservations()) {
            reservationServices.cancelBooking(res.getID());
        }
        for (Equipment eq : getInstance(id).getEquipments()) {
            eq.removeActivity(getInstance(id));
        }
        actRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The activity ID value is empty");
        return actRepository.existsById(id);
    }

    @Override
    public boolean booking(Integer activityID, Reservation reservation) throws IllegalStateException, AlreadyExistingException {
        Activity activity = getInstance(activityID);
        if (activity.addReservation(reservation)) {
            return true;
        } else return false;
    }

    @Override
    public boolean cancelBooking(Reservation toCancel, Integer activityID) {
        Activity act = getInstance(activityID);
        if (act.removeReservation(toCancel)) {
            return !actRepository.save(act).getReservations().contains(toCancel);
        } else return false;
    }

    public Set<Equipment> activityCreationErrorsChecking(LocalDateTime start, LocalDateTime stop, String[] equipmentsNames) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (start.isBefore(currentTime) || stop.isBefore(currentTime))
            throw new IllegalArgumentException(
                    "You are trying to create an activity with starting or ending time before than the current time");
        Set<Equipment> equipments = new HashSet<>();
        if (equipmentsNames.length == 0)
            return equipments;
        else {
            for (String equipmentName : equipmentsNames) {
                Optional<Equipment> equipment = eqRepository.findByName(equipmentName);
                if (equipment.isPresent())
                    equipments.add(equipment.get());
            }
            return equipments;
        }
    }

}
