package com.sliit.oop_serverapp.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("GUEST")
public class GuestAuthor extends Author {
    private String institution;
    private String country;
}
