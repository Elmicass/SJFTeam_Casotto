package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ISeaRowRepository extends JpaRepository<SeaRow, Integer> {

    @Query("select s from SeaRow s left join fetch s.seaRowMap left join fetch s.beachPlaces")
    List<SeaRow> findAll();
    
    boolean existsBySeaRowNumber(Integer seaRowNumber);

    Optional<SeaRow> findBySeaRowNumber(Integer seaRowNumber);

}
