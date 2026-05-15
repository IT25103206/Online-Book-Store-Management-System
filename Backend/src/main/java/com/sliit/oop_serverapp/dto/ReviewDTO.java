package com.sliit.oop_serverapp.dto;

import lombok.Data;

/**
 * OOP Concept: Encapsulation (Data Transfer Object)
 * This DTO encapsulates review data, allowing it to be securely transferred 
 * between the frontend and backend without exposing the internal entity structure.
 */
@Data
public class ReviewDTO {
    private Integer id;
    private Integer rating;
    private String comment;
    private Integer userId;
    private Integer bookId;
}
