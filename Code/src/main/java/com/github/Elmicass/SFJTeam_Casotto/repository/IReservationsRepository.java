package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;
import com.github.Elmicass.SFJTeam_Casotto.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationsRepository extends JpaRepository<Reservation, String> {

    List<Reservation> findByTimeslotAndUserEmail(TimeSlot timeSlot, User user);
    
}
