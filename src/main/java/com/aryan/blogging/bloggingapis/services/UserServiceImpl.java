package com.aryan.blogging.bloggingapis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.exceptions.ResourceNotFoundException;
import com.aryan.blogging.bloggingapis.payload.UserDTO;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired 
	ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userdto) {
		
		User user=this.dtoToUser(userdto);
		User savedUser=this.userRepo.save(user);
		return this.userToDTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userdto,Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id ",userId));
		
		user.setEmail(userdto.getEmail());
		user.setAbout(userdto.getAbout());
		user.setFirstname(userdto.getFirstname());
		user.setLastname(userdto.getLastname());
		user.setAbout(userdto.getAbout());
		user.setPassword(userdto.getPassword());

		User updatUser=this.userRepo.save(user);
		UserDTO dto= this.userToDTO(updatUser);

		return dto;
	}

	@Override
	public UserDTO getUserById(int userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id ",userId));
		return this.userToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users=this.userRepo.findAll();

		List<UserDTO> userDtos=users.stream().map(user->this.userToDTO(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id ",userId));
		this.userRepo.delete(user);

	}
	// private User dtoToUser(UserDTO userDTO)
	// {
	// 	User user=new User();
	// 	user.setId(userDTO.getId());
	// 	user.setFirstname(userDTO.getFirstname());
	// 	user.setLastname(userDTO.getLastname());
	// 	user.setAbout(userDTO.getAbout());
	// 	user.setPassword(userDTO.getPassword());
	// 	user.setEmail(userDTO.getPassword());
	// 	return user;
	// }
	// private UserDTO userToDTO(User user)
	// {
	// 	UserDTO userdto=new UserDTO();
	// 	userdto.setId(user.getId());
	// 	userdto.setFirstname(user.getFirstname());
	// 	userdto.setLastname(user.getLastname());
	// 	userdto.setAbout(user.getAbout());
	// 	userdto.setPassword(user.getPassword());
	// 	userdto.setEmail(user.getPassword());
	// 	return userdto;
	// }
	public User dtoToUser(UserDTO userDTO)
	{
		User user=this.modelMapper.map(userDTO,User.class);
		return user;
	}
	public UserDTO userToDTO(User user)
	{
		UserDTO userdto=this.modelMapper.map(user,UserDTO.class);
		return userdto;
	}
}
