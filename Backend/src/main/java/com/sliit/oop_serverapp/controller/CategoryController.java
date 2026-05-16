package com.sliit.oop_serverapp.controller;

import com.sliit.oop_serverapp.entity.Category;
import com.sliit.oop_serverapp.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OOP Concept: Encapsulation & Abstraction
 * CategoryController encapsulates category/genre management.
 * It provides access to the archive genres through a repository.
 */
@RestController
@RequestMapping("/Category")
public class CategoryController {

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get ALl Categories
    @GetMapping
    public List<Category> getAll(){
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

   

}
