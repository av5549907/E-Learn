package com.elearn.app.dtos;

import com.elearn.app.entities.Category;
import com.elearn.app.entities.Video;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class CourseDto {
    private  String courseId;

    @NotEmpty(message = "title should not be empty")
    @Size(min=3,max=50,message = "title must be between 3 to 50 characters")
    private  String title;
    @NotEmpty(message = "shortDesc should not be empty")
    @Size(min=3,max=50,message = "shortDesc must be between 3 to 50 characters")
    private  String shortDesc;
    private String longDesc;
    private double price;
    private  boolean live=false;
    private double discount;
    private  String banner;
    private Date createdDate;
    private List<Video> videoList=new ArrayList<>();
    private  List<Category> categoryList=new ArrayList<>();
}
