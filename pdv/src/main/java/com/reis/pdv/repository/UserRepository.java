package com.reis.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reis.pdv.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findUserByUsername(String username);
}