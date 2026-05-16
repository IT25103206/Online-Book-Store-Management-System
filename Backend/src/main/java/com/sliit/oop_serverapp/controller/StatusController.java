package com.sliit.oop_serverapp.controller;

import com.sliit.oop_serverapp.entity.Status;
import com.sliit.oop_serverapp.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OOP Concept: Encapsulation & Abstraction
 * StatusController encapsulates the system status endpoints.
 * It abstracts operational checks from the rest of the application.
 */
@RestController
@RequestMapping("/Status")

    // Get All Status
    @GetMapping
    public List<Status> getAll(){
        return statusRepository.findAll();
    }

    // Create Status
    @PostMapping("/Add")
    public ResponseEntity<String> createStatus(@RequestBody Status status){
        statusRepository.save(status);

        return ResponseEntity.ok("Status Created Successfully");
    }

    // Update Status - Pending/ Delivered
    @PutMapping("/Update")
    public String updateStatus (@RequestBody Status request){

        if (statusRepository.existsById(request.getId())){

            Status status = statusRepository.findById(request.getId()).get();

            status.setName(request.getName());
            statusRepository.save(status);

            return "Status Updated Sucessfully";
        }
        return "Status ID Not Found";
    }

    // Delete Status
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String>deleteStatus(@PathVariable int id){

        if(statusRepository.existsById(id)){

            statusRepository.deleteById(id);

            return ResponseEntity.ok("Status Deleted Sucessfully");
        }
        return ResponseEntity.badRequest().body("Status ID Not Found");
    }
}


// LOGIC - Manages Status Related Operations
/*
        Create New Order Statuses -
        Retrieve Available/Current Statuses
        Update the current Statuses of Orders - Pending -> Delivered
        Remove the Status from the database : After Delivered
*/
