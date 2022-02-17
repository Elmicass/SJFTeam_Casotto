package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdersRepository extends  JpaRepository<Order, Integer> {

    @Query("select o from Order o left join fetch o.products")
    List<Order> findAll();


    
}
