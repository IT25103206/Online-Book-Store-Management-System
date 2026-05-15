package com.sliit.oop_serverapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * OOP Concept: Encapsulation & Inheritance
 * User entity represents a registered actor in the system.
 * It serves as a base class for users with different roles.
 */
@Getter
@Setter
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 120)
    @Column(name = "name", length = 120)
    private String name;

    @Size(max = 80)
    @Column(name = "gmail", length = 80)
    private String gmail;

    @Size(max = 500)
    @Column(name = "password", length = 500)
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "isadmin")
    private Byte isadmin;

    public String getRoleMessage() {
        return "Logged in as Regular User: " + name;
    }

    public boolean authenticate(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }
}