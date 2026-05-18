package com.sliit.oop_serverapp.entity;

import jakarta.persistence.Column; // Maps fields to database columns
import jakarta.persistence.Embeddable; // Marks class as embeddable
import jakarta.validation.constraints.NotNull; // Validation for non-null values
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * CategoryHasBookId Class
 * ------------------------
 * This class represents the composite primary key for the
 * CategoryHasBook entity.
 *
 * It combines:
 * - categoryId
 * - bookId
 *
 * Purpose:
 * - Used to uniquely identify each record in the
 *   category_has_book junction table.
 *
 * OOP Concepts Used:
 * 1. Encapsulation
 *    - Fields are private and accessed using getters/setters.
 *
 * 2. Composition
 *    - Combines multiple attributes into a single key object.
 *
 * 3. Object Equality
 *    - Equals and HashCode methods are automatically generated
 *      using Lombok for proper object comparison.
 *
 * Technologies Used:
 * - JPA/Hibernate for ORM mapping
 * - Lombok for boilerplate code generation
 * - Jakarta Validation for field validation
 */

@Getter // Lombok automatically generates getter methods
@Setter // Lombok automatically generates setter methods
@EqualsAndHashCode // Generates equals() and hashCode() methods
@Embeddable // Marks this class as embeddable inside another entity

public class CategoryHasBookId implements Serializable {

    /**
     * Serializable version ID
     * Used during object serialization/deserialization
     */
    private static final long serialVersionUID = 4735200853095314828L;

    /**
     * Category ID
     * References the primary key of the Category entity
     */
    @NotNull // Value cannot be null
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    /**
     * Book ID
     * References the primary key of the Book entity
     */
    @NotNull // Value cannot be null
    @Column(name = "book_id", nullable = false)
    private Integer bookId;

}

