package com.garima.fitnesscentre.models;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.garima.fitnesscentre.models.data.Course;


public interface CourseRepository extends JpaRepository<Course, Integer>{
    
    @Query("SELECT p FROM Course p WHERE p.id != :id and p.slug = :slug and p.startDate = :startDate and p.duration = :duration")
    Course findBySlug(int id, String slug, String startDate, int duration);

    Page<Course> findAll(Pageable pageable);

}
