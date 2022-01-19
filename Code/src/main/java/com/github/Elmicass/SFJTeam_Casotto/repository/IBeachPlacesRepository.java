package com.github.Elmicass.SFJTeam_Casotto.repository;

import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBeachPlacesRepository extends JpaRepository<BeachPlace, String> {

    BeachPlace findBySeaRowAndSeaRowPosition(SeaRow seaRow, int position);
    
}
