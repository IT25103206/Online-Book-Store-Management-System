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

    @PostMapping("/Upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("File is empty");
            
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            
            // SAVE TO EXTERNAL PATH (More robust path construction for Windows/Linux)
            Path uploadPath = Paths.get(System.getProperty("user.home"), "lumina_books", "uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path path = uploadPath.resolve(fileName);
            Files.write(path, bytes);
            
            return ResponseEntity.ok("http://localhost:8080/uploads/" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed on server: " + e.toString());
        }
        
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


   
    







   