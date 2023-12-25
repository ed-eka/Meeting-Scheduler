package com.metrodata.serverapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metrodata.serverapp.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
    boolean existsByName(String name);
}
