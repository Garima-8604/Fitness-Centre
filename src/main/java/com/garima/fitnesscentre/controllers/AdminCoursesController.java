package com.garima.fitnesscentre.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garima.fitnesscentre.models.CategoryRepository;
import com.garima.fitnesscentre.models.CourseRepository;
import com.garima.fitnesscentre.models.TrainerRepository;
import com.garima.fitnesscentre.models.data.Category;
import com.garima.fitnesscentre.models.data.Course;
// import com.garima.fitnesscentre.models.data.Page;
import com.garima.fitnesscentre.models.data.Trainer;

@Controller

@RequestMapping("/admin/courses")
public class AdminCoursesController {
    
    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired 
    private TrainerRepository trainerRepo;

    @GetMapping
    public String index(Model model)
    {
        List<Course> courses = courseRepo.findAll();
        model.addAttribute("courses", courses);
        return "admin/courses/index";
    }

    @GetMapping("/add")
    public String add(Course course, Model model)
    {
        List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("trainers", trainers);
        return "admin/courses/add";
    }


}
