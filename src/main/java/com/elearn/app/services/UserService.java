package com.elearn.app.services;

import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
   UserDto createUser(UserDto userDto);

   UserDto updateUser(UserDto  userDto,String userId);

   CustomPageResponse getAllUsers(int pageNumber, int pageSize, String sortBy);
   UserDto getUserById(String userId);

   List<UserDto> getAll();

   String deleteUser(String userId);
}
