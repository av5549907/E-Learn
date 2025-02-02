package com.elearn.app.controllers;

import com.elearn.app.ApiResponse.JwtApiResponse;
import com.elearn.app.Config.security.JwtUtil;
import com.elearn.app.dtos.CustomUserDetails;
import com.elearn.app.dtos.LoginRequest;
import com.elearn.app.dtos.UserDto;
import com.elearn.app.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthenticationManager manager;
    UserDetailsService userDetailsService;
    JwtUtil jwtUtil;
    ModelMapper modelMapper;

    public AuthController(AuthenticationManager manager, UserDetailsService userDetailsService, JwtUtil jwtUtil, ModelMapper modelMapper) {
        this.manager = manager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authenticatedUser = manager.authenticate(authenticationToken);

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Incorrect email or password");
        }

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.getEmail());
        User user = userDetails.getUser();
        String token = jwtUtil.generateToken(user.getEmail());
        JwtApiResponse build = JwtApiResponse.builder().token(token).userDto(modelMapper.map(user, UserDto.class)).build();
        return ResponseEntity.ok(build);

    }

}
