package com.github.Elmicass.SFJTeam_Casotto.repository;

import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationsRepository extends JpaRepository<Reservation, String> {

    Reservation findByEntityIDAndTimeslotAndUserEmail(String entityID, TimeSlot timeSlot, String userEmail);
    
}
