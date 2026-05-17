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

    // Add New Category
    @PostMapping("/Add")
    public ResponseEntity<String> createCategory(@RequestBody Category request){
        Category category = new Category();

        category.setName(request.getName());
        categoryRepository.save(category);

        return ResponseEntity.ok("Category Created Successfully");
    }

    // Update Category * WHY WE NEED TO UPDATE A CATEGORY
    @PutMapping(path = "/Update")
    public String updateCategory(@RequestBody Category request){
        if(categoryRepository.existsById(request.getId())){

            Category category = categoryRepository.findById(request.getId()).get();

            category.setName(request.getName());
            categoryRepository.save(category);

            return "Category Updated Successfully";
        }
        return "Category ID not Found";
    }

    // Delete Category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteID(@PathVariable int id){
        if(categoryRepository.existsById(id)) {

            categoryRepository.deleteById(id);

            return ResponseEntity.ok("Category Deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Category ID Not Found");
    }

}
