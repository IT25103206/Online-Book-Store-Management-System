package com.sliit.oop_serverapp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * OOP Concept: Inheritance (Subclass)
 * PrintedBook extends the Book class, inheriting its common properties 
 * and adding physical-specific attributes like weight and dimensions.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("PRINTED")
public class PrintedBook extends Book {
    private Double weight;
    private String dimensions;

    @Override
    public String toString() {
        return "PrintedBook: " + getName() + " [Weight: " + weight + "kg]";
    }

    @Override
    public String getDisplayDetails() {
        return "Physical Copy: " + getName() + " [Weight: " + weight + "kg] - Ready for shipping";
    }
}
