package com.sliit.oop_serverapp.entity;

import jakarta.persistence.*; // JPA annotations for ORM mapping
import lombok.Getter;
import lombok.Setter;

/**
 * CategoryHasBook Entity Class
 * -----------------------------
 * This class represents the relationship between Category and Book entities.
 * It is used as a junction/bridge table for handling the many-to-many
 * relationship between categories and books.
 *
 * Database Table:
 * - category_has_book
 *
 * OOP Concepts Used:
 * 1. Encapsulation
 *    - Fields are kept private.
 *    - Access is provided through getters and setters.
 *
 * 2. Association
 *    - This class creates an association between Category and Book entities.
 *
 * 3. Composition of Keys
 *    - Uses a composite primary key through EmbeddedId.
 *
 * Technologies Used:
 * - JPA/Hibernate for ORM mapping
 * - Lombok for automatic getter/setter generation
 */

@Getter // Lombok automatically generates getter methods
@Setter // Lombok automatically generates setter methods
@Entity // Marks this class as a JPA entity
@Table(name = "category_has_book") // Maps this entity to the database table

public class CategoryHasBook {

    /**
     * Composite Primary Key
     * Combines categoryId and bookId
     */
    @EmbeddedId // Indicates a composite primary key
    private CategoryHasBookId id;

    /**
     * Many-to-One relationship with Category
     * Many records in category_has_book can belong to one category
     *
     * fetch = FetchType.LAZY
     * - Category data is loaded only when needed
     *
     * optional = false
     * - Category value is mandatory
     */
    @MapsId("categoryId") // Maps categoryId from composite key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Many-to-One relationship with Book
     * Many records in category_has_book can belong to one book
     *
     * fetch = FetchType.LAZY
     * - Book data is loaded only when needed
     *
     * optional = false
     * - Book value is mandatory
     */
    @MapsId("bookId") // Maps bookId from composite key
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}

