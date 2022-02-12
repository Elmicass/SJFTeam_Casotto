package com.github.Elmicass.SFJTeam_Casotto.repository;

import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISeaRowRepository extends JpaRepository<SeaRow, String> {

    boolean existsBySeaRowNumber(int seaRowNumber);

    SeaRow findBySeaRowNumber(int seaRowNumber);

}
