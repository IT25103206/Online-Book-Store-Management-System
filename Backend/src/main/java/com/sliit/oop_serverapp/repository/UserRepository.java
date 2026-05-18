package com.sliit.oop_serverapp.repository;

import com.sliit.oop_serverapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByGmail(String gmail);
}
