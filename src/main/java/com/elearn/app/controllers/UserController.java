package com.elearn.app.controllers;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.UserDto;
import com.elearn.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId) {
        return new ResponseEntity<>(userService.updateUser(userDto, userId), HttpStatus.OK);
    }

//    @GetMapping("/")
//    ResponseEntity<CustomPageResponse> getAllUsers(
//            @RequestParam(value="pageNumber",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
//            @RequestParam(value="pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
//            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstants.SORT_BY) String sortBy
//      ){
//        return  new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy),HttpStatus.OK);
//    }

    @GetMapping
    ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
}
