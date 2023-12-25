package com.metrodata.serverapp.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrodata.serverapp.entity.VerificationToken;


@Repository
@Transactional
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
    Optional<VerificationToken> findByToken(String token);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE verification_token v SET v.confirmed_date = ?1 WHERE v.token = ?2", nativeQuery = true)
    int updateConfirmedDate(LocalDateTime confirmedDate, String token);

    int deleteByAccountId(long id);
}