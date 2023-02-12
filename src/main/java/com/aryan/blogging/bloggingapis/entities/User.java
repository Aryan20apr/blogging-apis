package com.aryan.blogging.bloggingapis.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Entity
@Table(name = "Users") // If we do not specify this, table will be created with name User
@NoArgsConstructor
@Getter
@Setter

public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name", nullable = false, length = 50)
	private String firstname;

	@Column(name = "last_name", nullable = false, length = 50)
	private String lastname;
	@Column(name = "email", nullable = false, length = 50)
	private String email;
	@Column(name = "image", nullable = true, length = 200)
	private String imageName;

	private String imageurl;

	@Column(name = "password", nullable = false, length = 150)
	private String password;
	@Column(name = "about_user", nullable = false, length = 200)
	private String about;

	// One user has multiple posts
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY ) // Single post can have many comments
	private Set<Comment> comments = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_subscriptions",
    joinColumns = { @JoinColumn(name = "user_id") },
    inverseJoinColumns = { @JoinColumn(name = "cat_id") })
    private Set<Category> categories=new HashSet<>();

	@ManyToMany(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)//When use is created, role also created 
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

//********************************************** Methods from UserDetails ***************************************/
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub

		List<SimpleGrantedAuthority> authorities=roles.stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;//null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;//null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;//false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;//false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		//Indicates whether the user's credentials 
		//(password) has expired. Expired credentials prevent authentication.
		return true;//false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		//Indicates whether the user is enabled or 
		//disabled. A disabled user cannot be authenticated.
		return true;//false;
	}
}
