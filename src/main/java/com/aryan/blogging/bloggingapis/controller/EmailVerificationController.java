//package com.aryan.blogging.bloggingapis.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.aryan.blogging.bloggingapis.payload.UserDTO;
//import com.aryan.blogging.bloggingapis.services.EmailService;
//
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//
//@RestController
//@RequestMapping
//public class EmailVerificationController {
//
//    @Autowired
//    EmailService emailService;
//    
//    @PostMapping("/verifyEmail")
//    public ResponseEntity<?> verifyEmail(@RequestBody UserDTO user)
//    {Map<String,String> map=new HashMap<String, String>();
//        boolean sent=emailService.sendEmail(user.getEmail(),user.getEmail());
//        
//        
//            
//            map.put("vrified", sent+"");
//            map.put("email", user.getEmail());
//        
//        return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
//           
//        
//    }
//    
//}
