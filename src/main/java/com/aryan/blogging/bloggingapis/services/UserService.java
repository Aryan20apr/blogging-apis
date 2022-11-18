package com.aryan.blogging.bloggingapis.services;

import java.util.List;

import com.aryan.blogging.bloggingapis.payload.PasswordChangeDTO;
import com.aryan.blogging.bloggingapis.payload.UserDTO;
import com.aryan.blogging.bloggingapis.utils.Constants.PasswordChangeStatus;

public interface UserService {
	UserDTO registerNewUser(UserDTO user);
	
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user, Integer userId);
	
	UserDTO getUserById(int userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
	
	PasswordChangeStatus changePassword(PasswordChangeDTO passwordChangeDTO);

	PasswordChangeStatus forgotPassword(PasswordChangeDTO passwordChangeDTO);
}
