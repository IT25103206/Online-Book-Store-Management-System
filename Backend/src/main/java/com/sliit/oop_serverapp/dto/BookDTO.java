package com.sliit.oop_serverapp.dto;

import lombok.Data;


@Data
public class BookDTO {
    private Integer id;
    private String name;
    private String description;
    private String price;
    private Integer quantity;
    private Integer authorId;
    private String authorName;
    private String bookType;
    private String imagePath;
    private Boolean isBestseller;
    private String downloadUrl; // For EBook
    private Double fileSizeMb; // For EBook
    private Double weight; // For PrintedBook
    private String dimensions; // For PrintedBook
    private Integer categoryId;
}
