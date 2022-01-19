package com.github.Elmicass.SFJTeam_Casotto.repository;

import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivitiesRepository extends JpaRepository<Activity, String> {

    Activity findByNameAndTimeslot(String name, TimeSlot timeSlot);

}
