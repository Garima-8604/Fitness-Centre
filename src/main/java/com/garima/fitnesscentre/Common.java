package com.garima.fitnesscentre;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.garima.fitnesscentre.models.CategoryRepository;
import com.garima.fitnesscentre.models.PageRepository;
import com.garima.fitnesscentre.models.data.Category;
import com.garima.fitnesscentre.models.data.Page;

@ControllerAdvice
public class Common {

    @Autowired
    private PageRepository pageRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @ModelAttribute
    public void sharedData(Model model){
        List<Page> pages = pageRepo.findAllByOrderBySortingAsc();

        List<Category> categories = categoryRepo.findAll();

        model.addAttribute("cpages", pages);
        model.addAttribute("ccategories", categories);


    }
    
}
