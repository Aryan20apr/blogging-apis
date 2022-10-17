package com.aryan.blogging.bloggingapis.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
// @NoArgsConstructor
public class Post {

    public Post() {
        System.out.println("Inside default Post Constructor");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    private User user;
    // We can find the user if we have a post.

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // Single post can have many comments
    private Set<Comment> comments = new HashSet<>();
}
