package com.aryan.blogging.bloggingapis.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(nullable = false, unique = false)
    private String title;

    private String content;

    private String imageName;
    
    private String imageUrl;

    private String addedDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    private User user;
    // We can find the user if we have a post.

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // Single post can have many comments
    private Set<Comment> comments = new HashSet<>();
}
