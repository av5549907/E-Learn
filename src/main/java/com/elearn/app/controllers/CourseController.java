package com.elearn.app.controllers;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomMessage;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.services.CourseService;
import com.elearn.app.services.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto){
        return new ResponseEntity<>(courseService.createCourse(courseDto),HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable String courseId){
        return new ResponseEntity<>(courseService.getCourse(courseId),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourses(@RequestParam String keyword){
        return new ResponseEntity<>(courseService.searchCourses(keyword),HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    ResponseEntity<String> deleteCourse(@PathVariable String courseId){
        return new ResponseEntity<>(courseService.deleteCourse(courseId),HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<CustomPageResponse<CourseDto>> getAllCourses(
            @RequestParam(value="pageNumber",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value="pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstants.SORT_BY) String sortBy
    ){
        return new ResponseEntity<>(courseService.getAllCourses(pageNumber,pageSize,sortBy),HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<Page<CourseDto>> getAllCourses(Pageable pageable){

        return new ResponseEntity<>(courseService.getAllCourses(pageable),HttpStatus.OK);
    }

    @PutMapping("/{courseId}")
    ResponseEntity<CourseDto> updateCourseDetails(@Valid @RequestBody CourseDto courseDto,@PathVariable String courseId){
        return new ResponseEntity<>(courseService.updateCourseDetails(courseDto,courseId),HttpStatus.OK);
    }

    @Autowired
    FileService fileService;
    @PostMapping("/{courseId}/banners")
    ResponseEntity<CustomMessage> uploadBanner(
            @PathVariable String courseId,
            @RequestParam("banner")MultipartFile banner
    ) throws IOException {
        System.out.println(banner.getContentType());
        System.out.println(banner.getOriginalFilename());
        System.out.println(banner.getName());
        System.out.println(banner.getSize());
        String filepath=fileService.save(banner,AppConstants.COURSE_BANNER_UPLOAD_DIR,banner.getOriginalFilename());
        return ResponseEntity.ok(null);
    }
}
