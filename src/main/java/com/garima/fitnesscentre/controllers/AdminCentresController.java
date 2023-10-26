package com.garima.fitnesscentre.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.garima.fitnesscentre.models.CentreRepository;

import com.garima.fitnesscentre.models.data.Centre;


import jakarta.validation.Valid;

@Controller

@RequestMapping("/admin/centres")
public class AdminCentresController {

    @Autowired
    private CentreRepository centreRepo;

    @GetMapping
    public String index(Model model)
    {
        List<Centre> centres = centreRepo.findAll();
        model.addAttribute("centres", centres);
        return "admin/centres/index";
    }

    @GetMapping("/add")
    public String add(Centre centre)
    {
        return "admin/centres/add";
    }

    @PostMapping("/add")
    public String add(@Valid Centre centre, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "admin/centres/add";
        }
        redirectAttributes.addFlashAttribute("message", "Centre added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String city = centre.getCity();
        Centre cityExits = centreRepo.findByCity(centre.getId(),city);
        if(cityExits != null)
        {
            redirectAttributes.addFlashAttribute("message", "City exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("centre", centre);

        }
        else{
            centre.setCity(city);

            centreRepo.save(centre);

        }
    

        return "redirect:/admin/centres/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model)
    {
        Centre centre = centreRepo.getReferenceById(id);
        model.addAttribute("centre", centre);

        return "admin/centres/edit";


    }

    @PostMapping("/edit")
    public String edit(@Valid Centre centre, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {

        Centre centreCurrent = centreRepo.getReferenceById(centre.getId());

        if(bindingResult.hasErrors())
        {
            model.addAttribute("centre", centreCurrent.getCity());
            return "admin/centres/edit";
        }
        redirectAttributes.addFlashAttribute("message", "centre edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String city = centre.getCity();
        Centre cityExits = centreRepo.findByCity(centre.getId(),city);
        if(cityExits != null)
        {
            redirectAttributes.addFlashAttribute("message", "City exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("centre", centre);

        }
        else{
            centre.setCity(city);

            centreRepo.save(centre);

        }
        return "redirect:/admin/centres/edit/" + centre.getId();
    }

    @GetMapping("/delete/{id}")
    public String edit(@PathVariable int id, RedirectAttributes redirectAttributes)
    {
        centreRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Centre Deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/centres";

    }

    
}
