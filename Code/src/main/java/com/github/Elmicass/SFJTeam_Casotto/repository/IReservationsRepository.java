package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;
import com.github.Elmicass.SFJTeam_Casotto.model.User;
import com.github.Elmicass.SFJTeam_Casotto.model.IEntity.BookableEntityType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationsRepository extends JpaRepository<Reservation, Integer> {

    @Query("select r from Reservation r left join fetch r.timeslot left join fetch r.bpReference left join fetch r.actReference left join fetch r.joReference where user = :#{#user} and r.timeslot.start = :#{#start} and r.timeslot.stop = :#{#stop}")
    Optional<List<Reservation>> findByUserAndTimeslot(@Param("user") User user, @Param("start") LocalDateTime start, @Param("stop") LocalDateTime stop);

    List<Reservation> findByType(BookableEntityType type);
    
}
