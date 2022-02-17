package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEquipmentsRepository extends JpaRepository<Equipment, Integer> {

    @Query("select e from Equipment e left join fetch e.scheduledActivities")
    List<Equipment> findAll();

    Optional<Equipment> findByName(String name);
    
}
