package com.aryan.blogging.bloggingapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aryan.blogging.bloggingapis.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
}
