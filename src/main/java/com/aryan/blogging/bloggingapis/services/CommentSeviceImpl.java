package com.aryan.blogging.bloggingapis.services;

import java.time.LocalDate;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aryan.blogging.bloggingapis.entities.Comment;
import com.aryan.blogging.bloggingapis.entities.Post;
import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.exceptions.ResourceNotFoundException;
import com.aryan.blogging.bloggingapis.payload.CommentDto;
import com.aryan.blogging.bloggingapis.repositories.CommentRepo;
import com.aryan.blogging.bloggingapis.repositories.PostRepo;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;

@Service
public class CommentSeviceImpl implements CommentService{

    @Autowired
    private PostRepo postRepo;

    @Autowired UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) 
        {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);
        Date date=new Date();
        comment.setPost(post);
        comment.setUser(user);
        comment.setDate(date);
        Comment savedcomment=commentRepo.save(comment);

        return modelMapper.map(savedcomment,CommentDto.class);
     }

    @Override
    public void deleteComment(Integer commentId) {
        
        Comment comment=commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment id", commentId));
        commentRepo.delete(comment);
    }

    
}