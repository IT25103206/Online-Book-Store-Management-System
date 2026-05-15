package com.sliit.oop_serverapp.dto;

import lombok.Data;

/**
 * OOP Concept: Encapsulation (Data Transfer Object)
 * UserDTO encapsulates user profile and session data.
 */
@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String gmail;
    private String password;
    private Integer age;
    private Byte isadmin;
    private String userType;
}
