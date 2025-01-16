package com.elearn.app.dtos;

import com.elearn.app.entities.Category;
import com.elearn.app.entities.Video;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class CourseDto {
    private  String courseId;
    private  String title;
    private  String shortDesc;
    private String longDesc;
    private double price;
    private  boolean live=false;
    private double discount;
    private  String banner;
    private Date createdDate;
//    List<Video> videoList=new ArrayList<>();
//    private  List<Category> categoryList=new ArrayList<>();
}
