package com.aryan.blogging.bloggingapis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.blogging.bloggingapis.entities.User;
import com.aryan.blogging.bloggingapis.exceptions.ApiException;
import com.aryan.blogging.bloggingapis.payload.JwtAuthRequest;
import com.aryan.blogging.bloggingapis.payload.JwtAuthResponse;
import com.aryan.blogging.bloggingapis.payload.UserDTO;
import com.aryan.blogging.bloggingapis.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;//to authenticate username and password

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
        @RequestBody JwtAuthRequest request
    ) throws Exception
    {
        //Now authenticate
        this.authenticate(request.getUsername(),request.getPassword());
        //If authenticated successfully, generate token
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
        String token=jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        //response.setUser(this.mapper.map((User) userDetails, UserDTO.class));
        return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
        
    }
    private void authenticate(String username,String password) throws Exception
    {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password);
       try
       {
        this.authenticationManager.authenticate(authenticationToken);
       }
       catch(DisabledException e)
       {
        throw new DisabledException("User is disabled");
       }
       catch(BadCredentialsException e)
       {
        System.out.println("Invalid credentials "+e.getMessage());
        throw new ApiException("Invalid Username or password");//Exception("Invalid Username or password");
       }
    }
    
}
