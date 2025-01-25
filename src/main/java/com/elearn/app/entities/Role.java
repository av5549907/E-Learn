package com.elearn.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    private String roleId;
    private String roleName;
    @ManyToMany
    @JoinTable(name="roles_users")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
