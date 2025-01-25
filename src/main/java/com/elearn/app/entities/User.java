package com.elearn.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    private  String Id;
    private  String name;
    //username=email
    @Column(unique = true)
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

}
