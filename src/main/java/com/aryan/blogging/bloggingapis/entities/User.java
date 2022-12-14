package com.aryan.blogging.bloggingapis.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;


@Entity
@Table(name="Users")//If we do not specify this, table will be created with name User
@NoArgsConstructor
@Getter
@Setter

public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name",nullable = false, length=50)
	private String firstname;
	
	@Column(name="last_name",nullable = false, length=50)
	private String lastname;
	@Column(name="email",nullable = false, length=50)
	private String email;
	
	
	@Column(name="password",nullable = false, length=50)
	private String password;
	@Column(name="about_user",nullable = false, length=200)
	private String about;
	
	
}
