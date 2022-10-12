package com.aryan.blogging.bloggingapis.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String about;

}
