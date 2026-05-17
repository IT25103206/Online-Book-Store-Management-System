package com.sliit.oop_serverapp.controller;

// Importing UserDTO class for transferring user data
import com.sliit.oop_serverapp.dto.UserDTO;

// Importing UserService interface
import com.sliit.oop_serverapp.service.UserService;

// Used for automatic dependency injection
import org.springframework.beans.factory.annotation.Autowired;

// Used to send HTTP responses
import org.springframework.http.ResponseEntity;

// Importing Spring MVC annotations
import org.springframework.web.bind.annotation.*;

// Importing List collection
import java.util.List;

/**
 * OOP Concept: Abstraction
 * This controller handles API requests
 * related to user operations.
 */

// Marks this class as a REST API Controller
@RestController

// Base URL for all APIs in this controller
@RequestMapping("/User")

// Allows requests from frontend applications
@CrossOrigin
public class UserController {

    // Automatically injects UserService implementation object
    @Autowired
    private UserService userService;

    /**
     * LOGIN API
     * Handles user login requests.
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO loginDTO) {

        // Calls service layer login method
        UserDTO user = userService.login(loginDTO);

        // If login successful
        if (user != null) {

            // Return HTTP 200 OK with user data
            return ResponseEntity.ok(user);
        }

        // Return HTTP 401 Unauthorized if login fails
        return ResponseEntity.status(401).build();
    }

    /**
     * GET API
     * Retrieves all users from database.
     */
    @GetMapping
    public List<UserDTO> getAll() {

        // Calls service layer method
        return userService.getAllUsers();
    }

    /**
     * CREATE API
     * Adds a new user to database.
     */
    @PostMapping("/Add")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserDTO userDTO) {

        // Sends user data to service layer
        // and returns saved user
        return ResponseEntity.ok(
                userService.createUser(userDTO)
        );
    }

    /**
     * UPDATE API
     * Updates existing user details.
     */
    @PutMapping("/Update")
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UserDTO userDTO) {

        // Sends updated data to service layer
        return ResponseEntity.ok(
                userService.updateUser(userDTO)
        );
    }

    /**
     * DELETE API
     * Deletes user using user ID.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Integer id) {

        // Calls delete method from service layer
        userService.deleteUser(id);

        // Returns success response
        return ResponseEntity.ok(
                "User Deleted Successfully"
        );
    }

}