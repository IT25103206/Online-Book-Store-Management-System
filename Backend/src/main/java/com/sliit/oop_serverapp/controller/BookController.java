package com.sliit.oop_serverapp.controller;

import com.sliit.oop_serverapp.dto.BookDTO;
import com.sliit.oop_serverapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/Books")
@CrossOrigin
public class BookController {
     @Autowired
    private BookService bookService;

    

   
        
    }
    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAllBooks();
    }

    @PostMapping("/Add")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.createBook(bookDTO));
    }

    @PutMapping("/Update")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(bookDTO));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book Deleted Successfully");
    }
    

}


   
    







   