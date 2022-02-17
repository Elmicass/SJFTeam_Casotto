package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobOffersRepository extends JpaRepository<JobOffer, Integer> {

    @Query("select j from JobOffer j left join fetch j.applications")
    List<JobOffer> findAll();

    Optional<JobOffer> findByNameAndExpiration(String name, TimeSlot timeSlot);

}
