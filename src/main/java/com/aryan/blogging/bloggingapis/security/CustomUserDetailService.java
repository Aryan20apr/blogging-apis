package com.aryan.blogging.bloggingapis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.exceptions.ResourceNotFoundException;
import com.aryan.blogging.bloggingapis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;//Through this we can laod the user by user name
    @Override
    // Locates the user based on the username--See the Documentation by Ctrl+Click
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //loading user from database by username
        //For this a method findByEmail is created in the UserRepo
        User user=userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email", username));

        //Since we need to return the UserDetails but here we have the user, so we need to implement UserDetails in User entity
        return user;//null;
    }
    

    
}
