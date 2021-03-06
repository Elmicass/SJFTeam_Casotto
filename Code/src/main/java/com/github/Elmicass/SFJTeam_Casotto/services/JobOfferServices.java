package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.repository.IJobOffersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;

@Service
@Transactional
public class JobOfferServices implements IJobOfferServices {

    @Autowired
    private IJobOffersRepository joRepository;

    @Autowired
    @Lazy
    private IReservationServices reservationServices;

    @Override
    public JobOffer getInstance(Integer id) throws EntityNotFoundException {
        return joRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No job offer found with the given id: " + id));
    }

    @Override
    public List<JobOffer> getAll() {
        return joRepository.findAll();
    }

    @Override
    public JobOffer save(JobOffer jo) {
        return joRepository.save(jo);
    }

    @Override
    public boolean createJobOffer(@NonNull String name, @NonNull String description, @NonNull LocalDateTime start, @NonNull LocalDateTime expiration) throws AlreadyExistingException {
        if (jobOfferCreationErrorsChecking(start, expiration)) {
            JobOffer jobOffer = new JobOffer(name, description, start, expiration);
            if (joRepository.findByNameAndTimeslot(name, start, expiration).isPresent())
            throw new AlreadyExistingException(
                    "The activity you are trying to create already exists, with the same name: " + name
                            + " and same timeslot: " + start.toString() + " / " + expiration.toString());
            save(jobOffer);
            return true;
        } else return false;
    }

    @Override
    public boolean delete(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The job offer ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The job offer with ID: " + id + " does not exist");
        for (Reservation res : getInstance(id).getApplications()) {
            reservationServices.cancelBooking(res.getID());
        }
        joRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(Integer id) {
        if (id.toString().isBlank())
            throw new IllegalArgumentException("The job offer ID value is empty");
        return joRepository.existsById(id);
    }
    
    public boolean jobOfferCreationErrorsChecking(LocalDateTime start, LocalDateTime expiration) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (start.isBefore(currentTime) || expiration.isBefore(currentTime))
            throw new IllegalArgumentException(
                    "You are trying to create a job offer with starting or ending time before than the current time");
        return true;
    }

    public boolean application(Integer jobOfferID, Reservation reservation) {
        JobOffer jo = getInstance(jobOfferID);
        if (jo.addReservation(reservation)) {
            return true;
        } else return false;
    }

    @Override
    public boolean cancelApplication(Reservation toCancel, Integer jobOfferID) {
        JobOffer jo = getInstance(jobOfferID);
        if (jo.removeReservation(toCancel)) {
            save(jo);
            return true;
        } else return false;
    }


}
