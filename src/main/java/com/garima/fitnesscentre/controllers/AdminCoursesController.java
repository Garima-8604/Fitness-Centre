package com.garima.fitnesscentre.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.garima.fitnesscentre.models.CategoryRepository;
import com.garima.fitnesscentre.models.CourseRepository;
import com.garima.fitnesscentre.models.TrainerRepository;
import com.garima.fitnesscentre.models.data.Category;
import com.garima.fitnesscentre.models.data.Course;
import com.garima.fitnesscentre.models.data.Trainer;

import jakarta.validation.Valid;

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
    public String index(Model model, @RequestParam(value="page", required = false) Integer p) {

        int perPage = 10;
        int page = (p != null) ? p : 0;
        Pageable pageable = PageRequest.of(page, perPage);

        Page<Course> courses = courseRepo.findAll(pageable);
        List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();

        HashMap<Integer, String> cats = new HashMap<>();
        HashMap<Integer, String> trains = new HashMap<>();
        for (Category cat : categories) {
            cats.put(cat.getId(), cat.getName());
        }
        for (Trainer person : trainers) {
            trains.put(person.getId(), person.getName());
        }

        model.addAttribute("courses", courses);
        model.addAttribute("cats", cats);
        model.addAttribute("trains", trains);

        long count = courseRepo.count();
        double pageCount = Math.ceil((double)count/(double)perPage);
        model.addAttribute("pageCount", (int)pageCount);
        model.addAttribute("perPage", perPage); 
        model.addAttribute("count", count);   
        model.addAttribute("page", page);

        return "admin/courses/index";
    }

    @GetMapping("/add")
    public String add(Course course, Model model) {
        List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("trainers", trainers);
        return "admin/courses/add";
    }

    @PostMapping("/add")
    public String add(@Valid Course course, BindingResult bindingResult, MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws IOException {
        List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            model.addAttribute("trainers", trainers);
            return "admin/courses/add";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (filename.endsWith("jpg") || filename.endsWith("png")) {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Course added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = course.getName().toLowerCase().replace(" ", "-");

        Course slugExits = courseRepo.findBySlug(course.getId(), slug, course.getStartDate(), course.getDuration());

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("course", course);
        }

        else if (slugExits != null) {
            redirectAttributes.addFlashAttribute("message", "Course exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("course", course);

        } else {
            String startDateStr = course.getStartDate();
            int durationMonths = course.getDuration();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = startDate.plusMonths(durationMonths);
            String endDateStr = endDate.format(formatter);

            course.setEndDate(endDateStr);
            course.setSlug(slug);
            course.setImage(filename);
            courseRepo.save(course);
            Files.write(path, bytes);

        }

        return "redirect:/admin/courses/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        Course course = courseRepo.getReferenceById(id);
        model.addAttribute("course", course);
        List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("trainers", trainers);

        return "admin/courses/edit";

    }

    @PostMapping("/edit")
    public String edit(@Valid Course course, BindingResult bindingResult, MultipartFile file,
            RedirectAttributes redirectAttributes, Model model) throws IOException {

        Course currentCourse = courseRepo.getReferenceById(course.getId());

        List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();
        if (bindingResult.hasErrors()) {
            model.addAttribute("courseName", currentCourse.getName());
            model.addAttribute("categories", categories);
            model.addAttribute("trainers", trainers);
            return "admin/courses/edit";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (!file.isEmpty()) {
            if (filename.endsWith("jpg") || filename.endsWith("png")) {
                fileOK = true;
            }
        }
         else{
                fileOK = true;
            }

        redirectAttributes.addFlashAttribute("message", "Course edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = course.getName().toLowerCase().replace(" ", "-");

        Course slugExits = courseRepo.findBySlug(course.getId(), slug, course.getStartDate(), course.getDuration());

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Image must be a jpg or png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("course", course);
        }

        else if (slugExits != null) {
            redirectAttributes.addFlashAttribute("message", "Course exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("course", course);

        } else {
            String startDateStr = course.getStartDate();
            int durationMonths = course.getDuration();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = startDate.plusMonths(durationMonths);
            String endDateStr = endDate.format(formatter);

            course.setEndDate(endDateStr);
            course.setSlug(slug);


            if(!file.isEmpty())
            {
                Path path2 = Paths.get("src/main/resources/static/media/" + currentCourse.getImage());
                Files.delete(path2);
                course.setImage(filename);
                Files.write(path, bytes);

            }
            else{
            course.setImage(currentCourse.getImage());
            }

            courseRepo.save(course);


        }

        return "redirect:/admin/courses/edit/" + course.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) throws IOException
    {
        Course course = courseRepo.getReferenceById(id);
        Course currentCourse = courseRepo.getReferenceById(course.getId());

        Path path2 = Paths.get("src/main/resources/static/media/" + currentCourse.getImage());
        Files.delete(path2);
        courseRepo.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Course Deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/courses";

    }

}
