package com.garima.fitnesscentre.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.garima.fitnesscentre.models.CategoryRepository;
import com.garima.fitnesscentre.models.CourseRepository;
import com.garima.fitnesscentre.models.data.Category;
import com.garima.fitnesscentre.models.data.Course;

@Controller

@RequestMapping("/category")
public class CategoriesController {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private CourseRepository courseRepo;

    @GetMapping("/{slug}")
    public String category(@PathVariable String slug, Model model, @RequestParam(value="page", required = false) Integer p) {

        int perPage = 6;
        int page = (p != null) ? p : 0;
        Pageable pageable = PageRequest.of(page, perPage);
        long count = 0;

        if (slug.equals("all")) {

            Page<Course> courses = courseRepo.findAll(pageable);

            count = courseRepo.count();

            model.addAttribute("courses", courses);
        } else {

            Category category = categoryRepo.findBySlug(slug);

            if (category == null) {
                return "redirect:/";
            }

            int categoryId = category.getId();
            String categoryName = category.getName();
            List<Course> courses = courseRepo.findAllByCategoryId(Integer.toString(categoryId), pageable);

            count = courseRepo.countByCategoryId(Integer.toString(categoryId));

            model.addAttribute("courses", courses);
            model.addAttribute("categoryName", categoryName);
        }

        double pageCount = Math.ceil((double)count / (double)perPage);

        model.addAttribute("pageCount", (int)pageCount);
        model.addAttribute("perPage", perPage);
        model.addAttribute("count", count);
        model.addAttribute("page", page);

        return "courses";
    }


    
}
