package com.aryan.blogging.bloggingapis.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Id not required to be auto-generated as there will be maximum 2 roles normal
    // and admin
    private int id;
    // User
    private String name;// Role name
}
