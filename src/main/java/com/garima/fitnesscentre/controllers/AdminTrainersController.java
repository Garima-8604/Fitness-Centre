package com.garima.fitnesscentre.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.garima.fitnesscentre.models.CentreRespository;
import com.garima.fitnesscentre.models.TrainerRepository;

import com.garima.fitnesscentre.models.data.Centre;

import com.garima.fitnesscentre.models.data.Trainer;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/admin/trainers")
public class AdminTrainersController {

    @Autowired
    private TrainerRepository trainerRepo;

    @Autowired
    private CentreRespository centreRepo;

    @GetMapping
    public String index(Model model)
    {
        List<Trainer> trainers = trainerRepo.findAll();
        model.addAttribute("trainers", trainers);
        return "admin/trainers/index";
    }

    @GetMapping("/add")
    public String add(Trainer trainer, Model model)
    {
        List<Centre> centres = centreRepo.findAll();
        model.addAttribute("centres", centres);
        return "admin/trainers/add";
    }

    @PostMapping("/add")
    public String add(@Valid Trainer trainer, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "admin/trainers/add";
        }
        redirectAttributes.addFlashAttribute("message", "Trainer added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");



        trainerRepo.save(trainer);

    

        return "redirect:/admin/trainers/add";
    }



    
}
