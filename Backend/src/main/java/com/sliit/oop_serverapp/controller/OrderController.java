package com.sliit.oop_serverapp.controller;

import com.sliit.oop_serverapp.dto.OrderDTO;
import com.sliit.oop_serverapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OOP Concept: Encapsulation & Abstraction
 * OrderController encapsulates the order-related API endpoints.
 * It abstracts the complex order processing by delegating tasks to the OrderService.
 */
@RestController
@RequestMapping("/Order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;
    
}
