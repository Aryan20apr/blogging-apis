package com.aryan.blogging.bloggingapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aryan.blogging.bloggingapis.entities.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{
    
}
