package com.aryan.blogging.bloggingapis.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.UserDTO;
import com.aryan.blogging.bloggingapis.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    

    // POST- create user
    @Autowired
    private UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO)
    {
        UserDTO createUserDto=this.userService.createUser(userDTO);
        return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);

    }

    //PUT- user update
    @PutMapping("/{userId}") //userId is path URI variable
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO,@PathVariable("userId") Integer uid)
    {
        UserDTO updatedUser=this.userService.updateUser(userDTO, uid);
        return ResponseEntity.ok(updatedUser);
    }

    //Delete- delete user
    //@DeleteMapping("/userId")
    // public ResponseEntity<?> deleteUse(@PathVariable("user") Integer uid)
    // {
    //     this.deleteUse(uid);
    //     // return ResponseEntity.ok(Map.of("message","User Deleted Successfully"));
    //     return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
    // }
        @DeleteMapping("/{userId}")
      public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
    {
        this.userService.deleteUser(uid);
        // return ResponseEntity.ok(Map.of("message","User Deleted Successfully"));
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
    }

    //GET- get user
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

        //GET- get single user
        @GetMapping("/{userid}")
        public ResponseEntity<UserDTO> getSingleUser(@PathVariable("userid") Integer uid)
        {
            return ResponseEntity.ok(this.userService.getUserById(uid));
        }
}
