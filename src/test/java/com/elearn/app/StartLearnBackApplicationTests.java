package com.elearn.app;

import com.elearn.app.Config.security.JwtUtil;
import com.elearn.app.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StartLearnBackApplicationTests {

	@Autowired
	private CategoryService categoryService;
	@Test
	void contextLoads() {
	}

//	@Test
//	void createCourseCategoryRelationshipTest(){
//		categoryService.addCourseToCategory("1cd1dfa5-da03-48ab-a6d6-dd1b9544b012","2365e24f-6bea-4724-938d-ea93d1fb3452");
//	}

	@Autowired
	JwtUtil jwtUtil;
	@Test
	public void testJwt(){
		System.out.println("Token generation started");
		String token=jwtUtil.generateToken("test1");
		System.out.println("token "+token);
		System.out.println(jwtUtil.validateToken(token,"test1"));
		System.out.println(jwtUtil.extractUserName(token));
	}

}
