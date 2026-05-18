package com.sliit.oop_serverapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "book")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "book_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Size(max = 120)
    @Column(name = "name", length = 120)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 45)
    @Column(name = "price", length = 45)
    private String price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "is_bestseller")
    private Boolean isBestseller = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public String getDisplayDetails() {
        return "Book: " + name + " by " + (author != null ? author.getName() : "Unknown");
    }
}