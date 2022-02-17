package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.QrCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQrRepository extends JpaRepository<QrCode, Integer> {

    Optional<QrCode> findByName(String format);
    
    
}
