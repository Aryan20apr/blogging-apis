package com.aryan.blogging.bloggingapis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aryan.blogging.bloggingapis.repositories.UserRepo;

@SpringBootTest
class BloggingApisApplicationTests {

	@Test
	void contextLoads() {
	}
// ******************* custom Defined for testing *********************
@Autowired	
private UserRepo userRepo;
	@Test
	public void repoTest()
	{
		String className=this.userRepo.getClass().getName();
		String packageName=this.userRepo.getClass().getPackageName();

		System.out.println("Class Name="+className);
		System.out.println("Package Name="+packageName);
	}
}
