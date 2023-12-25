package com.metrodata.serverapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrodata.serverapp.entity.Invitation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Invitation i SET i.status.id = 6 WHERE i.participant.id = ?1")
    int confirmInvite(long participantID);

    @Transactional
    @Modifying
    @Query("UPDATE Invitation i SET i.status.id = 7 WHERE i.participant.id = ?1")
    int rejectInvite(long participantID);

    Optional<Invitation> findById(long id);
    
    @Transactional
    int deleteByMeetingId(long id);
}