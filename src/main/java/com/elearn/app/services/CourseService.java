package com.elearn.app.services;

import com.elearn.app.dtos.CourseDto;
import com.elearn.app.entities.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {

    String startService();

    CourseDto createCourse(CourseDto courseDto);

    List<CourseDto> getAllCourses();
    CourseDto updateCourse(CourseDto dto,String courseId);
    String deleteCourse(String courseID);

    CourseDto getCourse(String courseId);

    List<CourseDto> searchByTitle(String searchTitleKeyword);


}
