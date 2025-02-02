package com.elearn.app.controllers;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomMessage;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.ResourceContentType;
import com.elearn.app.services.CourseService;
import com.elearn.app.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
@CrossOrigin("*")  // that means any client can request the API and will get response
public class CourseController {
    @Autowired
    CourseService courseService;

    @Operation(summary = "Start The Course Service")
    @GetMapping("/start")
    public ResponseEntity<String> courseService() {
        String start = courseService.startService();
        return ResponseEntity.status(HttpStatus.OK).body(start);
    }

    @Operation(summary = "Create the course",
            description = "Pass the new course information to create a new course",
            tags = {"course operation"}
    )
    @ApiResponse(responseCode = "201", description = "Course Created Success")
    @ApiResponse(responseCode ="501", description = "Internal server error")
    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto) {
        return new ResponseEntity<>(courseService.createCourse(courseDto), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable String courseId) {
        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourses(@RequestParam String keyword) {
        return new ResponseEntity<>(courseService.searchCourses(keyword), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    ResponseEntity<String> deleteCourse(@PathVariable String courseId) {
        return new ResponseEntity<>(courseService.deleteCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<CustomPageResponse<CourseDto>> getAllCourses(
            @RequestParam(value = "pageNumber", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = AppConstants.SORT_BY) String sortBy
    ) {
        return new ResponseEntity<>(courseService.getAllCourses(pageNumber, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<Page<CourseDto>> getAllCourses(Pageable pageable) {

        return new ResponseEntity<>(courseService.getAllCourses(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Update Course",
    description = "Update the course for the given course id",
            tags = {"course operation"}
    )
    @ApiResponse(responseCode = "201",description = "Course Update Success")
    @ApiResponse(responseCode = "501",description = "Internal Server Error")
    @PutMapping("/{courseId}")
    ResponseEntity<CourseDto> updateCourseDetails(@Valid @RequestBody CourseDto courseDto, @PathVariable String courseId) {
        return new ResponseEntity<>(courseService.updateCourseDetails(courseDto, courseId), HttpStatus.OK);
    }


    @PostMapping("/{courseId}/banners")
    ResponseEntity<?> uploadBanner(
            @PathVariable String courseId,
            @RequestParam("banner") MultipartFile banner
    ) throws IOException {
        String bannerType = banner.getContentType();
        if (bannerType != null && (!bannerType.equalsIgnoreCase("image/png") || !bannerType.equalsIgnoreCase("image/jpeg"))) {
            CustomMessage customMessage = new CustomMessage();
            customMessage.setMessage("Invalid file !!");
            customMessage.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
        }
        System.out.println(banner.getContentType());
        System.out.println(banner.getOriginalFilename());
        System.out.println(banner.getName());
        System.out.println(banner.getSize());

        return ResponseEntity.ok(courseService.saveBanner(banner, courseId));
    }

    @GetMapping("/{courseId}/banners")
    ResponseEntity<Resource> serveBanner(
            @PathVariable String courseId,
            HttpServletRequest request
    ) {
        System.out.println(request.getContextPath());
        Enumeration<String> head = request.getHeaderNames();
        while (head.hasMoreElements()) {
            String header = head.nextElement();
            System.out.println(header + " : " + request.getHeader(header));
        }
        ResourceContentType resourceContentType = courseService.getCourseBannerById(courseId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(resourceContentType.getContentType())).body(resourceContentType.getResource());
    }
}
