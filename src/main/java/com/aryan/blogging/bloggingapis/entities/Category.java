package com.aryan.blogging.bloggingapis.entities;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@NoArgsConstructor
@Getter
@Setter

public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name="Title",length = 75,nullable = false,unique = true)
    private String categoryTitle;
    
    @Column(name="Description",length = 200,nullable = true)
    private String categoryDescription;
}
