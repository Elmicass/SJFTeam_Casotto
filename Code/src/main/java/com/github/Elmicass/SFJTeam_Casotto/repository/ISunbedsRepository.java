package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;
import com.github.Elmicass.SFJTeam_Casotto.model.Sunbed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ISunbedsRepository extends JpaRepository<Sunbed, Integer> {

    List<Sunbed> findByPriceList(PriceList pricelist);
    

}
