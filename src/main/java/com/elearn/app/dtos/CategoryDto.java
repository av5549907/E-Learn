package com.elearn.app.dtos;

import com.elearn.app.entities.Course;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CategoryDto {
    private  String Id;
    private String title;
    private  String desc;
    private Date addedDate;
//    List<Course> courses=new ArrayList<>();
}
