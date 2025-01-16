package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="courses")
@Data
public class Course {

    @Id
    private  String courseId;

    private  String title;
    private  String shortDesc;
    private String longDesc;
    private double price;
    private  boolean live=false;
    private double discount;
    private  String banner;
    private Date createdDate;
    @OneToMany(mappedBy = "course")
    private List<Video> videoList=new ArrayList<>();
    @ManyToMany
    private  List<Category> categoryList=new ArrayList<>();
}