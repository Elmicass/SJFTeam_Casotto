package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IActivitiesRepository extends JpaRepository<Activity, Integer> {

    @Query("select a from Activity a left join fetch a.equipments left join fetch a.reservations")
    List<Activity> findAll();

    Optional<Activity> findByNameAndTimeslot(String name, TimeSlot timeSlot);

}
