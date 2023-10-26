package com.garima.fitnesscentre.models.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, message ="Name should be atleast two characters")
    private String name;

    @Size(min=5, message ="Description should be atleast five characters")
    private String description;

    private String slug;

    private String image;

    @Pattern(regexp ="^[0-9]+([.][0-9]{1,2})?", message ="Expected format : 5, 5.99,15")
    private String price;

    @Pattern(regexp ="^[1-9][0-9]*", message ="Please Choose a Category")
    @Column(name="category_id")
    private String categoryId;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{2}$", message ="Expected format : 26-10-23")
     @Column(name="start_date")
    private String startDate;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{2}$", message ="Expected format : 26-10-23")
     @Column(name="end_date")
    private String endDate;

     @Column(name="trainer_id")
    private String trainerId;

    private int  duration; 
}
