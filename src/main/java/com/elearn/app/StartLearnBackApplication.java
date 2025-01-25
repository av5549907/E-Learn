package com.elearn.app;

import com.elearn.app.entities.User;
import com.elearn.app.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class StartLearnBackApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(StartLearnBackApplication.class, args);
	}

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
		User user1=new User();
		user1.setId(UUID.randomUUID().toString());
		user1.setEmail("test1@gmail.com");
		user1.setName("vijay");
		user1.setActive(true);
		user1.setEmailVerified(true);
		user1.setAbout("I am vijay");
		user1.setPassword(passwordEncoder.encode("test1"));
		user1.setPhone("7759087561");
		user1.setCreateAt(new Date());
		userRepo.findByEmail("test1@gmail.com").ifPresentOrElse(user -> {
			System.out.println(user1.getEmail());
		},()->{
			userRepo.save(user1);
			System.out.println("User details saved successfully");
		});

	}

//	@Bean
//	ModelMapper modelMapper(){
//		return new ModelMapper();
//	}
}
