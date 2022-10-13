package com.aryan.blogging.bloggingapis.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aryan.blogging.bloggingapis.entities.Category;
import com.aryan.blogging.bloggingapis.entities.Post;
import com.aryan.blogging.bloggingapis.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>
{

    //Custom Finder methods
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
}
