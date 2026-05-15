package com.sliit.oop_serverapp.dto;

import lombok.Data;

/**
 * OOP Concept: Encapsulation (Data Transfer Object)
 * AuthorDTO encapsulates the data required for author-related transfers.
 */
@Data
public class AuthorDTO {
    private Integer id;
    private String name;
    private String discription;
    private String authorType;
    private String institution;
    private String country;
}
