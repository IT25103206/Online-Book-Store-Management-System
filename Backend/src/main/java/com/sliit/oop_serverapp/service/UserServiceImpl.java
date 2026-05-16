package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.UserDTO;
import com.sliit.oop_serverapp.entity.Admin;
import com.sliit.oop_serverapp.entity.User;
import com.sliit.oop_serverapp.exception.ResourceNotFoundException;
import com.sliit.oop_serverapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OOP Concept: Abstraction & Polymorphism
 * UserServiceImpl implements UserService, handling authentication 
 * and user profile operations with specialized implementation logic.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user;
        if ("ADMIN".equalsIgnoreCase(userDTO.getUserType())) {
            user = new Admin();
        } else {
            user = new User();
        }
        updateEntityFromDTO(user, userDTO);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userDTO.getId()));
        updateEntityFromDTO(user, userDTO);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO login(UserDTO loginDTO) {
        User user = userRepository.findByGmail(loginDTO.getGmail());
        if (user != null && user.authenticate(loginDTO.getPassword())) {
            return convertToDTO(user);
        }
        return null;
    }

    
}
