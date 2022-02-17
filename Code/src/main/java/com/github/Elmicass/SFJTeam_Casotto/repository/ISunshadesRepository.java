package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunshade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISunshadesRepository extends JpaRepository<Sunshade, Integer> {

    Optional<Sunshade> findByBeachPlace(BeachPlace currentlyUsedIn);
    

}
