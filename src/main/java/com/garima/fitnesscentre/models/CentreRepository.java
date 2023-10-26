package com.garima.fitnesscentre.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.garima.fitnesscentre.models.data.Centre;


public interface CentreRepository extends JpaRepository<Centre, Integer>{
    Centre findByCity(String city);

    @Query("SELECT p FROM Centre p WHERE p.id != :id and p.city = :city")
    Centre findByCity(int id, String city);
}
