package com.sliit.oop_serverapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter

@Setter

@EqualsAndHashCode

@Embeddable

public class CategoryHasBookId implements Serializable {
    private static final long serialVersionUID = 4735200853095314828L;
    @NotNull
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @NotNull
    @Column(name = "book_id", nullable = false)
    private Integer bookId;


}