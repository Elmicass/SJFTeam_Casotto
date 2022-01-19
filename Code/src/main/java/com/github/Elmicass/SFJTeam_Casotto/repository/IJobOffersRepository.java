package com.github.Elmicass.SFJTeam_Casotto.repository;

import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobOffersRepository extends JpaRepository<JobOffer, String> {

    JobOffer findByNameAndExpiration(String name, TimeSlot timeSlot);

}
