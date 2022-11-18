package com.aryan.blogging.bloggingapis.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.annotations.common.util.StringHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aryan.blogging.bloggingapis.entities.Role;
import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.exceptions.ResourceNotFoundException;
import com.aryan.blogging.bloggingapis.payload.PasswordChangeDTO;
import com.aryan.blogging.bloggingapis.payload.UserDTO;
import com.aryan.blogging.bloggingapis.repositories.RoleRepo;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;
import com.aryan.blogging.bloggingapis.utils.Constants;
import com.aryan.blogging.bloggingapis.utils.Constants.PasswordChangeStatus;


@Service
public class UserServiceImpl implements UserService {
	
	

	@Autowired
	private UserRepo userRepo;

	@Autowired 
	ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

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
//		user.setPassword(userdto.getPassword());

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

	@Override
	public UserDTO registerNewUser(UserDTO userdto) {
		
		User user=modelMapper.map(userdto, User.class);

		//encoded the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// We assume that any user registering through the register API will be the normal user
		Role role=this.roleRepo.findById((Constants.NORMAL_ROLE_ID)).get();

		user.getRoles().add(role);
		User newUser=this.userRepo.save(user);


		return modelMapper.map(newUser, UserDTO.class);
	}

    @Override
    public PasswordChangeStatus changePassword(PasswordChangeDTO passwordChangeDTO) {
        
        Optional<User> optional= userRepo.findByEmail(passwordChangeDTO.getEmail());
        
        User user=optional.get();
        if(user==null)
        {
            return PasswordChangeStatus.USER_DOES_NOT_EXIST;
        }
        String password=user.getPassword();
       
      
       
        if(passwordEncoder.matches(passwordChangeDTO.getPassword(), password))
        {
            System.out.println("Password are same");
            String encodedPassword=passwordEncoder.encode(passwordChangeDTO.getNewpassword());
            user.setPassword(encodedPassword);
            User updatedUser=userRepo.save(user);
            return PasswordChangeStatus.PASSWORD_CHANGED;
        }
        else
        {
            return PasswordChangeStatus.PASSWORD_INCORRECT;
        }
        
        
      
    }
    @Override
    public PasswordChangeStatus forgotPassword(PasswordChangeDTO passwordChangeDTO) {
        
        Optional<User> optional= userRepo.findByEmail(passwordChangeDTO.getEmail());
        
        User user=optional.get();
        System.out.println(user.getEmail());
        if(user==null)
        {
            return PasswordChangeStatus.USER_DOES_NOT_EXIST;
        }
        
       
      
       
       
        String encodedPassword=passwordEncoder.encode(passwordChangeDTO.getNewpassword());
        user.setPassword(encodedPassword);
            User updatedUser=userRepo.save(user);
            return PasswordChangeStatus.PASSWORD_CHANGED;
        
       
        
        
      
    }
}
