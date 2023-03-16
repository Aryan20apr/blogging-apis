package com.aryan.blogging.bloggingapis;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.aryan.blogging.bloggingapis.entities.Role;
import com.aryan.blogging.bloggingapis.repositories.RoleRepo;
import com.aryan.blogging.bloggingapis.utils.Constants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@SpringBootApplication
public class BloggingApisApplication implements CommandLineRunner{

//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BloggingApisApplication.class, args);
	}
		
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(this.passwordEncoder.encode("xyz"));
		
		
		try {
			Role adminrole=new Role();
			adminrole.setId(Constants.ADMIN_ROLE_ID);
			adminrole.setName("ROLE_ADMIN");//Note-This should be written as ROLE_ADMIN only so that it is detected properly when using swagger

			Role normalrole=new Role();
			normalrole.setId(Constants.NORMAL_ROLE_ID);//Note-This should be written as ROLE_NORMAL only so that it is detected properly when using swagger
			normalrole.setName("ROLE_NORMAL");

			List<Role> list=List.of(adminrole,normalrole);
			List<Role> result=roleRepo.saveAll(list);//This will insert the two roles into the table and try to update it if already present
			result.forEach(r->{
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			System.out.println("Role Already Exist");
		}
	}
	
	@Bean
	public WebMvcConfigurer customConfigurer() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	            configurer.defaultContentType(MediaType.APPLICATION_JSON);
	        }
	    };
	}
	
	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException {
	    GoogleCredentials googleCredentials = GoogleCredentials
	            .fromStream(new  ClassPathResource("firebase-service-account.json").getInputStream());
	   System.out.println("GoogleCredentials is null"+(googleCredentials==null));
	    FirebaseOptions firebaseOptions = FirebaseOptions
	            .builder()
	            .setCredentials(googleCredentials)
	            .build();
	    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
	    return FirebaseMessaging.getInstance(app);
	}



	

}
