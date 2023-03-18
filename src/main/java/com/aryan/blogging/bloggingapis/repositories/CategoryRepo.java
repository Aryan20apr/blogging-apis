package com.aryan.blogging.bloggingapis.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aryan.blogging.bloggingapis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{
    
    @Query("Select c from Category c where c.categoryId not in(Select c.categoryId from c.users p where p.id=:id)")
    List<Category> getUnsubscribedCategories(int id);
    
}
