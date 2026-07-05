package com.aryan.blogging.bloggingapis.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aryan.blogging.bloggingapis.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{
    
}
