package com.metrodata.serverapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrodata.serverapp.entity.Account;


@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsernameOrPerson_Email(String username, String email);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.isActive = TRUE WHERE a.username = ?1")
    int activatedAccount(String username);

    Optional<Account> findById(long id);
}