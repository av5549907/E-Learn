package com.elearn.app.services;

import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.dtos.ResourceContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CourseService {

    String startService();

    CourseDto createCourse(CourseDto courseDto);

    CustomPageResponse<CourseDto> getAllCourses(int pageNumber, int pageSize, String sortBy);

    Page<CourseDto> getAllCourses(Pageable pageable);
    CourseDto updateCourse(CourseDto dto,String courseId);
    String deleteCourse(String courseID);

    CourseDto getCourse(String courseId);

    List<CourseDto> searchCourses(String searchTitleKeyword);

    CourseDto updateCourseDetails(CourseDto courseDto,String courseId);


    CourseDto saveBanner(MultipartFile file,String courseId) throws IOException;


    ResourceContentType getCourseBannerById(String courseId);
}
