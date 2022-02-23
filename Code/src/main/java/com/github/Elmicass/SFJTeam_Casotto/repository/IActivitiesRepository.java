package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.Activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IActivitiesRepository extends JpaRepository<Activity, Integer> {

    @Query("select a from Activity a left join fetch a.equipments left join fetch a.reservations")
    List<Activity> findAll();

    @Query("select a from Activity a left join fetch a.timeslot where name = :#{#name} and a.timeslot.start = :#{#start} and a.timeslot.stop = :#{#stop}")
    Optional<Activity> findByNameAndTimeslot(@Param("name") String name, @Param("start") LocalDateTime start, @Param("stop") LocalDateTime stop);

}
