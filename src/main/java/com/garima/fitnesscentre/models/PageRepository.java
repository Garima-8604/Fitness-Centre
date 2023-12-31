package com.garima.fitnesscentre.models;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.garima.fitnesscentre.models.data.Page;
// import java.util.List;


public interface PageRepository extends JpaRepository<Page, Integer>{

    Page findBySlug(String slug);

    @Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
    Page findBySlug(int id, String slug);

    // Page findBySlugAndIdNot(String slug, int id);
    List<Page> findAllByOrderBySortingAsc();
}
