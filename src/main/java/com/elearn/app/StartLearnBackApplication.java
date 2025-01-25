package com.elearn.app;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.entities.Role;
import com.elearn.app.entities.User;
import com.elearn.app.repositories.RoleRepo;
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
	RoleRepo roleRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {
		Role role1=new Role();
		Role role2=new Role();
		role1.setRoleId(UUID.randomUUID().toString());
		role1.setRoleName(AppConstants.ROLE_ADMIN);

		role2.setRoleId(UUID.randomUUID().toString());
		role2.setRoleName(AppConstants.ROLE_GUEST);

		roleRepo.findByRoleName(AppConstants.ROLE_ADMIN).ifPresentOrElse(role -> {
			role1.setRoleId(role.getRoleId());
			System.out.println(role1.getRoleName()+" is already present in database");
		},()->{
			roleRepo.save(role1);
			System.out.println("User role saved successfully");
		});

		roleRepo.findByRoleName(AppConstants.ROLE_GUEST).ifPresentOrElse(role -> {
			role2.setRoleId(role.getRoleId());
			System.out.println(role2.getRoleName()+" is already present in database");
		},()->{
			roleRepo.save(role2);
			System.out.println("User role saved successfully");
		});

		User user1=new User();
		user1.setId(UUID.randomUUID().toString());
		user1.setEmail("test1@gmail.com");
		user1.setName("Vijay");
		user1.setActive(true);
		user1.setEmailVerified(true);
		user1.setAbout("I am vijay");
		user1.setPassword(passwordEncoder.encode("test1"));
		user1.setPhone("7759087561");
		user1.setCreateAt(new Date());
		user1.assignRole(role1);
		user1.assignRole(role2);
		userRepo.findByEmail("test1@gmail.com").ifPresentOrElse(user -> {
			System.out.println(user1.getEmail());
		},()->{
			userRepo.save(user1);
			System.out.println("User details saved successfully");
		});

		User user2=new User();
		user2.setId(UUID.randomUUID().toString());
		user2.setEmail("test@gmail.com");
		user2.setName("Adarsh");
		user2.setActive(true);
		user2.setEmailVerified(true);
		user2.setAbout("I am adarsh");
		user2.setPassword(passwordEncoder.encode("test"));
		user2.setPhone("7759086178");
		user2.setCreateAt(new Date());
		user2.assignRole(role2);
		userRepo.findByEmail("test@gmail.com").ifPresentOrElse(user -> {
			System.out.println(user2.getEmail());
		},()->{
			userRepo.save(user2);
			System.out.println("User details saved successfully");
		});

		User user3=new User();
		user3.setId(UUID.randomUUID().toString());
		user3.setEmail("priya@gmail.com");
		user3.setName("Priya");
		user3.setActive(true);
		user3.setEmailVerified(true);
		user3.setAbout("I am a software developer priya");
		user3.setPassword(passwordEncoder.encode("priya"));
		user3.setPhone("8859086178");
		user3.setCreateAt(new Date());
		user3.assignRole(role2);
		userRepo.findByEmail("priya@gmail.com").ifPresentOrElse(user -> {
			System.out.println(user3.getEmail());
		},()->{
			userRepo.save(user3);
			System.out.println("User details of Priya saved successfully");
		});

//		User user4=new User();
//		user4.setId(UUID.randomUUID().toString());
//		user4.setEmail("shakti@gmail.com");
//		user4.setName("Shakti");
//		user4.setActive(true);
//		user4.setEmailVerified(true);
//		user4.setAbout("I am shaktimaan");
//		user4.setPassword(passwordEncoder.encode("shakti"));
//		user4.setPhone("8859086188");
//		user4.setCreateAt(new Date());
//		user4.assignRole(role1);
//		userRepo.findByEmail("shakti@gmail.com").ifPresentOrElse(user -> {
//			System.out.println(user4.getEmail());
//		},()->{
//			userRepo.save(user4);
//			System.out.println("User details of Priya saved successfully");
//		});

	}

//	@Bean
//	ModelMapper modelMapper(){
//		return new ModelMapper();
//	}
}
