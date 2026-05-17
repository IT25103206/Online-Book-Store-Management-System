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

    // Handles POST requests for uploading a book image
@PostMapping("/Upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Checks whether the uploaded file is empty
            if (file.isEmpty()) return ResponseEntity.badRequest().body("File is empty");
            
            // Creates a unique file name using current time
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Converts the uploaded file into bytes
            byte[] bytes = file.getBytes();
            
            // SAVE TO EXTERNAL PATH (More robust path construction for Windows/Linux)
            // Defines the external folder path to store uploaded images
            Path uploadPath = Paths.get(System.getProperty("user.home"), "lumina_books", "uploads");

            // Creates the upload folder if it does not already exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Creates the full path for the uploaded file
            Path path = uploadPath.resolve(fileName);

            // Writes/saves the file bytes into the upload folder
            Files.write(path, bytes);
            
            // Returns the uploaded image URL to the frontend
            return ResponseEntity.ok("http://localhost:8080/uploads/" + fileName);
        } catch (Exception e) {
            // Prints the error details in the console
            e.printStackTrace();

            // Returns an error message if upload fails
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


   
    







   