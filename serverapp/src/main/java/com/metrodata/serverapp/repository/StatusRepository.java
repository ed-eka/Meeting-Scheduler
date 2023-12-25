package com.metrodata.serverapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metrodata.serverapp.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}