package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

/**
 * OOP Concept: Abstraction (Data Access Layer)
 * AuthorRepository abstracts the database operations for Author entities.
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {


    
}
