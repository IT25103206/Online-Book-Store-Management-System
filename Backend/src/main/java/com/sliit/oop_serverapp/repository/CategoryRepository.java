package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * OOP Concept: Abstraction (Data Access Layer)
 * CategoryRepository provides an abstract interface for genre data access.
 * It hides the complexity of SQL operations through Spring Data JPA.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
