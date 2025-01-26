package com.elearn.app.servicesImpl;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.UserDto;
import com.elearn.app.entities.Category;
import com.elearn.app.entities.Role;
import com.elearn.app.entities.User;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.RoleRepo;
import com.elearn.app.repositories.UserRepo;
import com.elearn.app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    ModelMapper modelMapper;
    UserRepo userRepo;

    RoleRepo roleRepo;

    PasswordEncoder passwordEncoder;
    public UserServiceImpl(ModelMapper modelMapper, UserRepo userRepo,RoleRepo roleRepo,PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.roleRepo=roleRepo;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        User user=modelMapper.map(userDto,User.class);
        user.setId(userId);
        user.setCreateAt(new Date());
        user.setEmailVerified(false);
        user.setSmsVerified(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setProfilePath(AppConstants.DEFAULT_USER_PROFILE_PATH);
        Role role=roleRepo.findByRoleName(AppConstants.ROLE_GUEST).orElseThrow(()->new ResourceNotFoundException("Role not found"));
        user.assignRole(role);
        User savedUser =userRepo.save(user);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found !!"));
        User updatedUser=modelMapper.map(userDto,User.class);
        user.setPhone(updatedUser.getPhone());
        user.setCreateAt(updatedUser.getCreateAt());
        user.setAbout(updatedUser.getAbout());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setActive(updatedUser.isActive());
        userRepo.save(user);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public CustomPageResponse getAllUsers(int pageNumber,int pageSize,String sortBy) {
        Sort sort=Sort.by(sortBy);
        PageRequest pageRequest=PageRequest.of(pageNumber,pageSize,sort);
        Page<User> userPage=userRepo.findAll(pageRequest);
        List<User> all=userPage.getContent();
        List<UserDto> userDtoList=all.stream().map(cat->modelMapper.map(cat,UserDto.class)).collect(Collectors.toList());
        CustomPageResponse<UserDto> customPageResponse=new CustomPageResponse<UserDto>();
        customPageResponse.setContent(userDtoList);
        customPageResponse.setLast(userPage.isLast());
        customPageResponse.setTotalElements(userPage.getTotalElements());
        customPageResponse.setTotalPages(userPage.getTotalPages());
        customPageResponse.setPageSize(userPage.getSize());
        customPageResponse.setPageNumber(pageNumber);
        return customPageResponse;

    }

    @Override
    public UserDto getUserById(String userId) {
        User user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));
        return  modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users= userRepo.findAll();
        return users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public String deleteUser(String userId) {
        User user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found !!"));
        userRepo.delete(user);
        return "User with Id"+userId+" is deleted Successfully";
    }
}
