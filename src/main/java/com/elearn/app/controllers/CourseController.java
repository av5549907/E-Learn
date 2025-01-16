package com.elearn.app.controllers;

import com.elearn.app.dtos.CourseDto;
import com.elearn.app.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/start")
    public ResponseEntity<String> courseService(){
        String start=courseService.startService();
        return ResponseEntity.status(HttpStatus.OK).body(start);
    }

    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto){
        return new ResponseEntity<>(courseService.createCourse(courseDto),HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable String courseId){
        return new ResponseEntity<>(courseService.getCourse(courseId),HttpStatus.OK);
    }

    @GetMapping("/course/{title}")
    public ResponseEntity<List<CourseDto>> searchByTitle(@PathVariable String searchTitleKeyword){
        return new ResponseEntity<>(courseService.searchByTitle(searchTitleKeyword),HttpStatus.OK);
    }

    @DeleteMapping("/course/{courseId}")
    ResponseEntity<String> deleteCourse(@PathVariable String courseID){
        return new ResponseEntity<>(courseService.deleteCourse(courseID),HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<List<CourseDto>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(),HttpStatus.OK);
    }
}
