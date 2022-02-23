package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBeachPlacesRepository extends JpaRepository<BeachPlace, Integer> {
    
    @Query("select b from BeachPlace b left join fetch b.reservations")
    List<BeachPlace> findAll();

    Optional<BeachPlace> findBySeaRowAndSeaRowPosition(SeaRow seaRow, int position);
    
}
