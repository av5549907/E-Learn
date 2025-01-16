package com.elearn.app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category {
    @Id
    private  String Id;

    private String title;
    @Column(name = "description")
    private  String desc;

    private Date addedDate;

    @ManyToMany(mappedBy = "categoryList")
    List<Course> courses=new ArrayList<>();


}
