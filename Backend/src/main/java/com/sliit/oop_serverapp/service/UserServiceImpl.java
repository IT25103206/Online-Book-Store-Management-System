package com.sliit.oop_serverapp.service;

// Importing DTO class used to transfer user data
import com.sliit.oop_serverapp.dto.UserDTO;

// Importing Admin entity class
import com.sliit.oop_serverapp.entity.Admin;

// Importing User entity class
import com.sliit.oop_serverapp.entity.User;

// Custom exception for handling missing resources
import com.sliit.oop_serverapp.exception.ResourceNotFoundException;

// Repository used for database operations
import com.sliit.oop_serverapp.repository.UserRepository;

// Spring annotation for automatic dependency injection
import org.springframework.beans.factory.annotation.Autowired;

// Marks this class as a Service layer component
import org.springframework.stereotype.Service;

// Importing List collection
import java.util.List;

// Importing Collectors for stream operations
import java.util.stream.Collectors;

/**
 * OOP Concept: Abstraction & Polymorphism
 * UserServiceImpl implements UserService interface
 * and provides actual business logic implementation.
 */
@Service // Registers this class as a Spring Service Bean
public class UserServiceImpl implements UserService {

    // Automatically injects UserRepository object
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all users from database
     * and converts them into DTO objects.
     */
    @Override
    public List<UserDTO> getAllUsers() {

        // findAll() gets all users from database
        // stream() processes the list
        // map() converts User -> UserDTO
        // collect() converts stream back to List
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new user account.
     */
    @Override
    public UserDTO createUser(UserDTO userDTO) {

        // Declaring User reference variable
        User user;

        // Checking if the user type is ADMIN
        if ("ADMIN".equalsIgnoreCase(userDTO.getUserType())) {

            // Polymorphism: Admin object assigned to User reference
            user = new Admin();

        } else {

            // Create normal User object
            user = new User();
        }

        // Copy DTO values into Entity object
        updateEntityFromDTO(user, userDTO);

        // Save user to database and return DTO version
        return convertToDTO(userRepository.save(user));
    }

    /**
     * Updates existing user details.
     */
    @Override
    public UserDTO updateUser(UserDTO userDTO) {

        // Find user by ID
        // If not found, throw custom exception
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userDTO.getId()
                        ));

        // Update entity values using DTO data
        updateEntityFromDTO(user, userDTO);

        // Save updated user and return DTO
        return convertToDTO(userRepository.save(user));
    }

    /**
     * Deletes a user from database.
     */
    @Override
    public void deleteUser(Integer id) {

        // Check whether user exists
        if (!userRepository.existsById(id)) {

            // Throw exception if user not found
            throw new ResourceNotFoundException(
                    "User not found with id: " + id
            );
        }

        // Delete user using ID
        userRepository.deleteById(id);
    }

    /**
     * Handles user login authentication.
     */
    @Override
    public UserDTO login(UserDTO loginDTO) {

        // Find user using Gmail
        User user = userRepository.findByGmail(loginDTO.getGmail());

        // Check whether user exists and password is correct
        if (user != null &&
                user.authenticate(loginDTO.getPassword())) {

            // Return logged user details
            return convertToDTO(user);
        }

        // Return null if login fails
        return null;
    }

    /**
     * Converts User Entity into UserDTO.
     */
    private UserDTO convertToDTO(User user) {

        // Creating DTO object
        UserDTO dto = new UserDTO();

        // Copying values from entity to DTO
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setGmail(user.getGmail());
        dto.setAge(user.getAge());
        dto.setIsadmin(user.getIsadmin());

        // Checking object type using instanceof
        dto.setUserType(user instanceof Admin ? "ADMIN" : "USER");

        // Return DTO object
        return dto;
    }

    /**
     * Updates Entity object using DTO values.
     */
    private void updateEntityFromDTO(User user, UserDTO dto) {

        // Setting entity values from DTO
        user.setName(dto.getName());
        user.setGmail(dto.getGmail());
        user.setPassword(dto.getPassword());
        user.setAge(dto.getAge());
        user.setIsadmin(dto.getIsadmin());
    }
}