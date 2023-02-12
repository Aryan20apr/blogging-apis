package com.aryan.blogging.bloggingapis.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aryan.blogging.bloggingapis.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
    Optional<User> findByEmail(String email);
    //A container object which may or may not contain a non-null value. If a value is present, isPresent() returns true. If no value is present, the object is considered empty and isPresent() returns false.
}
