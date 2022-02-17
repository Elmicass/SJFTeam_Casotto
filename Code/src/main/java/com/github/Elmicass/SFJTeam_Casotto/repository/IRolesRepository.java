package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String string);

    

}
