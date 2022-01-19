package com.github.Elmicass.SFJTeam_Casotto.repository;

import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPriceListRepository extends JpaRepository<PriceList, String> {

    boolean existByName(String priceListName);

    PriceList findByName(String string);
    

}
