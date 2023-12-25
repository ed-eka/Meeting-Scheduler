package com.metrodata.serverapp.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrodata.serverapp.entity.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long>{
    Optional<Meeting> findByStatus (long status);

    @Query(value = 
    "SELECT m.* FROM meeting m JOIN invitation i ON m.id = i.meeting_id WHERE i.participant_id = ?1 && date_time_start >= NOW() && m.status_id != 3 && m.status_id != 4 ORDER BY date_time_start LIMIT 1", 
    nativeQuery = true)
    Meeting getByDateStart(long participantId);

    @Query(value = 
    "SELECT m.* FROM meeting m JOIN invitation i ON m.id = i.meeting_id WHERE i.participant_id = ?1 ORDER BY date_time_start", 
    nativeQuery = true)
    List<Meeting> getByPerson(long participantId);

    //Select total meeting by participant
    @Query(value = 
    "SELECT s.id, s.name, COUNT(*) AS count FROM meeting m JOIN status s ON m.status_id = s.id JOIN invitation i ON m.id = i.meeting_id WHERE i.participant_id = ?1 GROUP BY s.id",
    nativeQuery = true)
    List<Map<String, Object>> countByStatus(long id);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE meeting SET status_id = 4 WHERE id = ?1",nativeQuery = true)
    int updateStatus(long id);
}
