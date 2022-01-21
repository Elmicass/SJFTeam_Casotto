package com.github.Elmicass.SFJTeam_Casotto.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.github.Elmicass.SFJTeam_Casotto.model.ConfirmationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {

    Optional<ConfirmationToken> findByToken(String token);

    boolean existsByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

    void deleteByToken(String token);

}
