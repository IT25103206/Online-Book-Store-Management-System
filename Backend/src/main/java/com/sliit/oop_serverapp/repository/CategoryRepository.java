
        package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OOP Concept: Abstraction & Interface
 *
 * CategoryRepository acts as the Data Access Layer (DAL) for Category entities.
 * It provides abstract methods for database operations without writing SQL queries manually.
 *
 * By extending JpaRepository:
 * - CRUD operations are automatically available
 * - Spring Data JPA handles database communication internally
 * - Reduces boilerplate code and improves maintainability
 *
 * Entity Managed  : Category
 * Primary Key Type: Integer
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Additional custom query methods can be added here if needed

}

