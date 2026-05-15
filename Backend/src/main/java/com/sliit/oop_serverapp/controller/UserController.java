package com.sliit.oop_serverapp.controller;

import com.sliit.oop_serverapp.dto.UserDTO;
import com.sliit.oop_serverapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO loginDTO) {
        UserDTO user = userService.login(loginDTO);

        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build();
    }
    
}
