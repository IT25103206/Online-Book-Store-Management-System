package com.sliit.oop_serverapp.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Integer id;
    private Integer rating;
    private String comment;
    private Integer userId;
    private Integer bookId;
}
