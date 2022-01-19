package com.github.Elmicass.SFJTeam_Casotto.services;

import java.time.LocalDateTime;

import javax.persistence.EntityNotFoundException;

import com.github.Elmicass.SFJTeam_Casotto.exception.AlreadyExistingException;
import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;
import com.github.Elmicass.SFJTeam_Casotto.repository.IJobOffersRepository;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;

public class JobOfferServices implements IJobOfferServices {

    @Autowired
    private IJobOffersRepository joRepository;

    @Override
    public JobOffer getInstance(String id) throws EntityNotFoundException {
        return joRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No job offer found with the given id: " + id));
    }

    @Override
    public boolean createJobOffer(@NonNull String name, @NonNull String description, @NonNull LocalDateTime start, @NonNull LocalDateTime expiration) throws AlreadyExistingException {
        if (jobOfferCreationErrorsChecking(start, expiration)) {
            JobOffer jobOffer = new JobOffer(name, description, start, expiration);
            if (joRepository.findByNameAndExpiration(name, new TimeSlot(start, expiration)).equals(jobOffer))
            throw new AlreadyExistingException(
                    "The activity you are trying to create already exists, with the same name: " + name
                            + " and same timeslot: " + start.toString() + " / " + expiration.toString());
            joRepository.save(jobOffer);
            return true;
        } else return false;
    }

    @Override
    public boolean delete(String id) {
        if (id.isBlank())
            throw new IllegalArgumentException("The job offer ID is empty");
        if (!(exists(id)))
            throw new EntityNotFoundException("The job offer with ID: " + id + " does not exist");
        joRepository.deleteById(id);
        return !exists(id);
    }

    @Override
    public boolean exists(String id) {
        if (id.isBlank())
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


}
