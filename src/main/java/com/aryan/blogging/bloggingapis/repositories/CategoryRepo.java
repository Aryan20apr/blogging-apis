package com.aryan.blogging.bloggingapis.repositories;



import org.springframework.data.jpa.repository.JpaRepository;


import com.aryan.blogging.bloggingapis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{
    
    
    
}
