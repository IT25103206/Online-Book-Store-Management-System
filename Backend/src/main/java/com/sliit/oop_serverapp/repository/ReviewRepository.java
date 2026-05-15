package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * OOP Concept: Abstraction (Data Access Layer)
 * ReviewRepository provides an abstract interface for managing user reviews.
 */
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByBookId(Integer bookId);
}
