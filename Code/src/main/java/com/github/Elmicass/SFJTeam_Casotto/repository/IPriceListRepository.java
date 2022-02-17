package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPriceListRepository extends JpaRepository<PriceList, Integer> {

    boolean existsByName(String name);

    Optional<PriceList> findByName(String name);
    

}
