package com.elearn.app.dtos;

import com.elearn.app.entities.Role;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private  String Id;
    private  String name;
    private  String email;
    private  String phone;
    private  String password;
    private  String about;
    private  boolean active;
    private  boolean emailVerified;
    private  boolean smsVerified;
    private String profilePath;
    private Date createAt;
    private  String recentOtp;
    private Set<Role> roles=new HashSet<>();
}
