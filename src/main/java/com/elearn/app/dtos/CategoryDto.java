package com.elearn.app.dtos;

import com.elearn.app.entities.Course;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CategoryDto {
    private  String Id;
    @NotEmpty(message="title is required !!")
    @Size(min=3,max=50,message = "title must be between 3 to 50 Characters !!")
    private String title;
    @NotEmpty(message = "description is required")
    private  String desc;
    private Date addedDate;
    List<Course> courses=new ArrayList<>();
}
