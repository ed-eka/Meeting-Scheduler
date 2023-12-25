package com.metrodata.serverapp.repository;

import com.metrodata.serverapp.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query(value = "UPDATE confirmation_token c SET c.confirm_date = ?1 WHERE v.confirm_token = ?2", nativeQuery = true)
    int updateConfirmedDate(LocalDateTime confirmDate, String token);
}