package com.learning.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.employeemanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByName(String name);
}
