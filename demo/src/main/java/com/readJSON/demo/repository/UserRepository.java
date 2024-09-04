package com.readJSON.demo.repository;

import com.readJSON.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {


}


//. Spring Data JPA will automatically provide implementations for common operations
//        like saving, finding, deleting, and counting User entities based on the methods defined in JpaRepository.