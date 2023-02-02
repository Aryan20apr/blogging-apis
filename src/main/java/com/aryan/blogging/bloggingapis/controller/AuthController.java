package com.aryan.blogging.bloggingapis.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.blogging.bloggingapis.exceptions.ApiException;
import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.JwtAuthRequest;
import com.aryan.blogging.bloggingapis.payload.JwtAuthResponse;
import com.aryan.blogging.bloggingapis.payload.OTPSentResponse;
import com.aryan.blogging.bloggingapis.payload.PasswordChangeDTO;
import com.aryan.blogging.bloggingapis.payload.UserDTO;
import com.aryan.blogging.bloggingapis.security.JwtTokenHelper;
import com.aryan.blogging.bloggingapis.services.EmailService;
import com.aryan.blogging.bloggingapis.services.OTPService;
import com.aryan.blogging.bloggingapis.services.UserService;
import com.aryan.blogging.bloggingapis.utils.Constants.PasswordChangeStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Random random = new Random(100000);

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;// to authenticate username and password

    @Autowired
    private UserService userService;

    @Autowired
    OTPService otpService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request) throws Exception {
        // Now authenticate
        this.authenticate(request.getUsername(), request.getPassword());
        // If authenticated successfully, generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setMessage("Login Successfull");
        response.setSuccess(true);
        response.setToken(token);
        // response.setUser(this.mapper.map((User) userDetails, UserDTO.class));
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled");
        } catch (BadCredentialsException e) {
            System.out.println("Invalid credentials " + e.getMessage());
            throw new ApiException("Invalid Username or password");// Exception("Invalid Username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerNewUser(userDTO);
        ApiResponse<UserDTO> apiResponse=new ApiResponse<UserDTO>(registeredUser,"User registered successfully",true);
        return new ResponseEntity<ApiResponse<UserDTO>>(apiResponse, HttpStatus.CREATED);

    }

    @Autowired
    EmailService emailService;

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
    @PostMapping("/sendotp")
    public ResponseEntity<?> sendOTP(@RequestParam String email, HttpSession session) {
        // Generate OTP of 6 digit

        // int otp=random.nextInt(1000000);
        // session.setAttribute("otp", otp);
        
        session.setAttribute("email", email);
        
        
        boolean b = emailService.sendEmail(email);

        if (b) {
            OTPSentResponse response = new OTPSentResponse(email, "OTP Sent Successfully",true);
            return new ResponseEntity<OTPSentResponse>(response, HttpStatus.OK);
        } else {
            OTPSentResponse response = new OTPSentResponse(email, "Could not send otp. Enter a valid email id.",false);
            return new ResponseEntity<OTPSentResponse>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<?> verifyOTP(@RequestParam int otp, HttpSession session) {
        // Integer myOtp=(int)session.getAttribute("otp");

        String email = (String) session.getAttribute("email");// We can use session to remeber ceratin things
        int serverOtp = otpService.getOtp(email);
        ApiResponse response = new ApiResponse();

        System.out.println("Otp is " + serverOtp + " " + otp);

        if (serverOtp == otp) {
            System.out.println("##$%^");
            response.setMessage("Email verified");
            response.setSuccess(true);
            otpService.clearOTP(email);

        } else {

            response.setMessage("OTP does not match");
            response.setSuccess(false);
        }
        return new ResponseEntity<ApiResponse>(response, HttpStatus.UNAUTHORIZED);
    }
    
    @PostMapping("/forgotpassword")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody PasswordChangeDTO passinfo )
    {
        
        PasswordChangeStatus b=userService.forgotPassword(passinfo);
        ApiResponse response=new ApiResponse();
        
        if(PasswordChangeStatus.PASSWORD_CHANGED==b)
        {
            response.setMessage("Password changed successfully");
            response.setSuccess(true);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
        }
     
        else {
            response.setMessage("User Does Not exist with this email");
            response.setSuccess(false);
            return new ResponseEntity<ApiResponse>(response,HttpStatus.UNAUTHORIZED);
        }
        
    }

    
   
     

}
