package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobOffersRepository extends JpaRepository<JobOffer, Integer> {

    @Query("select j from JobOffer j left join fetch j.applications")
    List<JobOffer> findAll();

    @Query("select j from JobOffer j left join fetch j.timeslot where name = :#{#name} and j.timeslot.start = :#{#start} and j.timeslot.stop = :#{#stop}")
    Optional<JobOffer> findByNameAndTimeslot(@Param("name") String name, @Param("start") LocalDateTime start, @Param("stop") LocalDateTime stop);

}
