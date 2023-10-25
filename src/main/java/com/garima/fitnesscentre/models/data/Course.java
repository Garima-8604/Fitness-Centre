package com.garima.fitnesscentre.models.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private String slug;

    private String image;

    private String price;

    @Column(name="category_id")
    private String categoryId;

     @Column(name="start_date")
    private String startDate;

     @Column(name="end_date")
    private String endDate;

     @Column(name="trainer_id")
    private String trainerId;

    private int  duration; 
}
