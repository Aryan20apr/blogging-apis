package com.aryan.blogging.bloggingapis.services;

import java.util.List;

import com.aryan.blogging.bloggingapis.payload.UserDTO;

public interface UserService {
	
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user, Integer userId);
	
	UserDTO getUserById(int userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);

}
