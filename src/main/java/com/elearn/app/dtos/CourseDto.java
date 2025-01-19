package com.elearn.app.dtos;

import com.elearn.app.entities.Category;
import com.elearn.app.entities.Video;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private double discount;// to hide the discount in json when serialization(conversion of object in json) is going.
    private  String banner;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-mm-dd")//To chnage the Date format in response
    private Date createdDate;
    private String bannerContentType;
    private List<Video> videoList=new ArrayList<>();
    private  List<Category> categoryList=new ArrayList<>();

    public String getBannerUrl(){
        return "http://localhost:8080/api/v1/courses/"+courseId+"/banners";
    }
}
