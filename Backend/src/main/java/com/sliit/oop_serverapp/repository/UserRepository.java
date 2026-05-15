package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * OOP Concept: Abstraction (Data Access Layer)
 * UserRepository abstracts the database operations for user-related data.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByGmail(String gmail);
}
