package com.aryan.blogging.bloggingapis.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.PasswordChangeDTO;
import com.aryan.blogging.bloggingapis.payload.UserDTO;
import com.aryan.blogging.bloggingapis.services.UserService;
import com.aryan.blogging.bloggingapis.utils.Constants.PasswordChangeStatus;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    

    // POST- create user
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO)
    {
        UserDTO createUserDto=this.userService.createUser(userDTO);
        ApiResponse<UserDTO> apiResponse=new ApiResponse<UserDTO>(createUserDto,"User created successfully",true);
        return new ResponseEntity<ApiResponse<UserDTO>>(apiResponse,HttpStatus.CREATED);

    }

    //PUT- user update
    @PutMapping("/{userId}") //userId is path URI variable
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@Valid @RequestBody UserDTO userDTO,@PathVariable("userId") Integer uid)
    {
        UserDTO updatedUser=this.userService.updateUser(userDTO, uid);
        ApiResponse<UserDTO> apiResponse=new ApiResponse<UserDTO>(updatedUser,"User created successfully",true);
        return new ResponseEntity<ApiResponse<UserDTO>>(apiResponse,HttpStatus.CREATED);
        
    }
    
    // Update Password
    @PostMapping("/changepassword")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody PasswordChangeDTO passinfo )
    {
        PasswordChangeStatus b=userService.changePassword(passinfo);
        ApiResponse<String> response=new ApiResponse<String>();
        
        if(PasswordChangeStatus.PASSWORD_CHANGED==b)
        {
            response.setData(passinfo.getEmail());
            response.setMessage("Password changed successfully");
            response.setSuccess(true);
            return new ResponseEntity<ApiResponse<String>>(response,HttpStatus.OK);
        }
        else if(PasswordChangeStatus.PASSWORD_INCORRECT==b)
        {
            response.setData(passinfo.getEmail());
            response.setMessage("Password entered is incorrect");
            response.setSuccess(false);
            return new ResponseEntity<ApiResponse<String>>(response,HttpStatus.UNAUTHORIZED);
        }
        else {
            response.setData(passinfo.getEmail());
            response.setMessage("User Does Not exist with this email");
            response.setSuccess(false);
            return new ResponseEntity<ApiResponse<String>>(response,HttpStatus.UNAUTHORIZED);
        }
        
    }
    

    //Delete- delete user
    //@DeleteMapping("/userId")
    // public ResponseEntity<?> deleteUse(@PathVariable("user") Integer uid)
    // {
    //     this.deleteUse(uid);
    //     // return ResponseEntity.ok(Map.of("message","User Deleted Successfully"));
    //     return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
    // }
        @PreAuthorize("hasRole('ADMIN')")//uSER WILL admin role will only have this method's access
        //Annotation for specifying a method access-control expression which will be evaluated to decide whether a method invocation is allowed or not.
        @DeleteMapping("/{userId}")
      public ResponseEntity<ApiResponse<Integer>> deleteUser(@PathVariable("userId") Integer uid)
    {
        this.userService.deleteUser(uid);
        
        // return ResponseEntity.ok(Map.of("message","User Deleted Successfully"));
        return new ResponseEntity<ApiResponse<Integer>>(new ApiResponse<Integer>(uid,"User Deleted Successfully",true),HttpStatus.OK);
    }

    //GET- get user
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

        //GET- get single user
        @GetMapping("/{userid}")
        public ResponseEntity<ApiResponse<UserDTO>> getSingleUser(@PathVariable("userid") Integer uid)
        {
            ApiResponse<UserDTO> apiResponse=new ApiResponse<UserDTO>(this.userService.getUserById(uid),"User obtained ",true); 
            return ResponseEntity.ok(apiResponse);
        }

       
}
