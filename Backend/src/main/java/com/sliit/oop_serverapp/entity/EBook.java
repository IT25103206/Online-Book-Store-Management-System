package com.sliit.oop_serverapp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * OOP Concept: Inheritance (Subclass)
 * EBook extends the Book class, inheriting its common properties 
 * and adding digital-specific attributes like downloadUrl.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("EBOOK")
public class EBook extends Book {
    private String downloadUrl;
    private Double fileSizeMb;

    @Override
    public String toString() {
        return "EBook: " + getName() + " [Link: " + downloadUrl + "]";
    }

    @Override
    public String getDisplayDetails() {
        return "Digital Edition: " + getName() + " (E-Book) - Available for download";
    }
}
