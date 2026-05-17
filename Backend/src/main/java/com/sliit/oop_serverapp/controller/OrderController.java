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

    @GetMapping
    public List<OrderDTO> getAll() {
        return orderService.getAllOrders();
    }


    @PutMapping("/Update")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(orderDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order Deleted Successfully");
    }
}
