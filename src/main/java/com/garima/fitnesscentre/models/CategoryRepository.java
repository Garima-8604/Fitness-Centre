package com.garima.fitnesscentre.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garima.fitnesscentre.models.data.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
    Category findByName(String name);
}