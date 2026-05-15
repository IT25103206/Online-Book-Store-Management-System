package com.sliit.oop_serverapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * OOP Concept: Encapsulation & Data Modeling
 * Review entity represents a user's feedback for a book.
 * Private fields and Lombok accessors demonstrate encapsulation.
 */
@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rating")
    private Integer rating;

    @Lob
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
