package com.aryan.blogging.bloggingapis.entities;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name="comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String content;
    
    @Column(name="date_of_comment")
    private Date date;

    @ManyToOne//One comment can belong to only one post
    private Post post;

     @ManyToOne//One comment can be made by one user only
     private User user;
    
     private String firstName;
     private String lastName;
}
