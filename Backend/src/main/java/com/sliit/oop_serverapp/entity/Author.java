package com.sliit.oop_serverapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * OOP Concept: Encapsulation & Inheritance
 * Author entity represents a writer in the system.
 * It uses a SINGLE_TABLE inheritance strategy to distinguish between different author types.
 */
@Getter
@Setter
@Entity
@Table(name = "author")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "author_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("AUTHOR")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Lob
    @Column(name = "discription")
    private String discription;


}