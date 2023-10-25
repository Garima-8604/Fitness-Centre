package com.garima.fitnesscentre.models;

import org.springframework.data.jpa.repository.JpaRepository;
import com.garima.fitnesscentre.models.data.Course;


public interface CourseRepository extends JpaRepository<Course, Integer>{
    
}
