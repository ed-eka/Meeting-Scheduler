package com.metrodata.serverapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.metrodata.serverapp.entity.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{
    Optional<Person> findByEmailOrName(String email, String name);
    
    @Query(value =
    "SELECT * FROM person WHERE is_visible = true",
    nativeQuery = true)
    List<Person> getByVisibility();
}