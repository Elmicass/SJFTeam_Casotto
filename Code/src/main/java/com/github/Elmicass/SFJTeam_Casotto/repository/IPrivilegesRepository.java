package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrivilegesRepository extends JpaRepository<Privilege, Integer> {

    Optional<Privilege> findByName(String name);

    
    
}
