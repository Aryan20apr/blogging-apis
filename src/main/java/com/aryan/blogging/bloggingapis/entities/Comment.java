package com.aryan.blogging.bloggingapis.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    
}
