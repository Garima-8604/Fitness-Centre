package com.garima.fitnesscentre.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garima.fitnesscentre.models.data.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer>{
    
}
