package com.aryan.blogging.bloggingapis.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private int id;
	//@NotNull
	//@NotBlank//Also checks for NULL Using NotNull or NotBlank with Size will generate incorrect messages
	@Size(min = 2,message = "First name cannot be less than 2 characters in length")
	private String firstname;
	
	//@NotNull
	// @NotBlank
	@Size(min = 2,message = "Last name cannot be less than 2 characters in length")
	private String lastname;
	

	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message="Enter a valid email address")
	private String email;
	
	
	//@NotNull
	// @NotBlank
	@Size(min = 8,max=15,message = "Password must have atleast 6 characters and maximum 15 characters with atleast one Capital letter, special chracter and digit")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	private String password;

	// @NotBlank
	@Size(min = 20,max=150,message = "About cannot be less than 20 characters")
	private String about;

	private Set<RoleDTO> roles = new HashSet<>();

}
