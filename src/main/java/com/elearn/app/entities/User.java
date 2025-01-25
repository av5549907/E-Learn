package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
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

    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Role> roles=new HashSet<>();

    public  void assignRole(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void  removeRoles(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
