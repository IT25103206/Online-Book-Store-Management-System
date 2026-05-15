package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 * OOP Concept: Abstraction (Data Access Layer)
 * OrderRepository abstracts the persistence of customer transactions.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
