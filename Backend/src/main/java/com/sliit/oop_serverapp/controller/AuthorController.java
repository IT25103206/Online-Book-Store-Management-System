package com.sliit.oop_serverapp.controller;

import com.sliit.oop_serverapp.dto.AuthorDTO;
import com.sliit.oop_serverapp.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OOP Concept: Encapsulation & Abstraction
 * AuthorController encapsulates author-related operations.
 * It provides an abstract interface for managing author data.
 */
@RestController
@RequestMapping("/Author")
@CrossOrigin
public class AuthorController {

    @Autowired
    private AuthorService authorService;
   
    @PostMapping("/add")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(authorDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author Deleted Successfully");
    }
}
