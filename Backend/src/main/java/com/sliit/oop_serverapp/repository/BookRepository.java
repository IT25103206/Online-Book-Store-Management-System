package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * OOP Concept: Abstraction (Data Access Layer)
 * BookRepository provides an abstract interface for book persistence logic.
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
}
