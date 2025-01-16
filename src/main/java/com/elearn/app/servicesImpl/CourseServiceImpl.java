package com.elearn.app.servicesImpl;

import com.elearn.app.dtos.CourseDto;
import com.elearn.app.entities.Course;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.CourseRepo;
import com.elearn.app.services.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepo courseRepo;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public String startService() {

        return "Course Service started";
    }


    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        String Id= UUID.randomUUID().toString();
        courseDto.setCourseId(Id);
        courseDto.setCreatedDate(new Date());
        Course course=modelMapper.map(courseDto,Course.class);
        courseRepo.save(course);
        return modelMapper.map(course,CourseDto.class);
   }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses=courseRepo.findAll();
        List<CourseDto> courseDto=courses.stream().map(x->modelMapper.map(x,CourseDto.class)).collect(Collectors.toList());
        return courseDto;
    }

    @Override
    public CourseDto updateCourse(CourseDto dto, String courseId) {
        Course course=courseRepo.findByCourseId(courseId);
        course.setCourseId(dto.getCourseId());
        course.setCreatedDate(dto.getCreatedDate());
        course.setLive(dto.isLive());
        course.setBanner(dto.getBanner());
        course.setDiscount(dto.getDiscount());
        course.setPrice(dto.getPrice());
        course.setTitle(dto.getTitle());
        course.setLongDesc(dto.getLongDesc());
        course.setShortDesc(dto.getShortDesc());
        CourseDto courseDto=modelMapper.map(course,CourseDto.class);
        return courseDto;
    }

    @Override
    public String deleteCourse(String courseID) {
       Course course= courseRepo.findById(courseID).orElseThrow(()->new ResourceNotFoundException("Course Not Found"));
       courseRepo.delete(course);
       return "Course with "+courseID+" is deleted";
    }

    @Override
    public CourseDto getCourse(String courseId) {
        Course course=courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course not found"));
        return modelMapper.map(course,CourseDto.class);
    }

    @Override
    public List<CourseDto> searchByTitle(String searchTitleKeyword) {
        Optional<Course> course=courseRepo.findByTitle(searchTitleKeyword);
        return course.stream().map(cs->modelMapper.map(cs,CourseDto.class)).collect(Collectors.toList());

    }
}
