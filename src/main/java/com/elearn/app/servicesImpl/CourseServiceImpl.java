package com.elearn.app.servicesImpl;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.*;
import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.CourseRepo;
import com.elearn.app.services.CourseService;
import com.elearn.app.services.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepo courseRepo;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileService fileService;
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
    public CustomPageResponse<CourseDto> getAllCourses(int pageNumber, int pageSize, String sortBy) {
        CustomPageResponse<CourseDto> customPageResponse=new CustomPageResponse();
        Sort sort=Sort.by(sortBy);
        PageRequest pageRequest=PageRequest.of(pageNumber,pageSize,sort);
        Page<Course> coursePage=courseRepo.findAll(pageRequest);
        List<Course> courses=courseRepo.findAll();
        List<CourseDto> courseDto=courses.stream().map(x->modelMapper.map(x,CourseDto.class)).collect(Collectors.toList());
        customPageResponse.setContent(courseDto);
        customPageResponse.setLast(coursePage.isLast());
        customPageResponse.setTotalElements(coursePage.getTotalElements());
        customPageResponse.setTotalPages(coursePage.getTotalPages());
        customPageResponse.setPageSize(coursePage.getSize());
        customPageResponse.setPageNumber(pageNumber);
        return customPageResponse;
    }

    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        Page<Course> courses=courseRepo.findAll(pageable);
        List<CourseDto> courseDtos=courses.getContent().stream().map(course -> modelMapper.map(course,CourseDto.class)).collect(Collectors.toList());
        return new PageImpl<>(courseDtos,pageable,courses.getTotalElements());

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
//        modelMapper.map(dto,course);
        courseRepo.save(course);
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
    public List<CourseDto> searchCourses(String keyword) {
        List<Course> course=courseRepo.findByTitleContainingIgnoreCaseOrShortDescContainingIgnoreCase(keyword,keyword);
        return course.stream().map(cs->modelMapper.map(cs,CourseDto.class)).collect(Collectors.toList());

    }

    @Override
    public CourseDto updateCourseDetails(CourseDto courseDto, String courseId) {
        Course course=courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course not found !!"));
        course.setCourseId(courseId);
        course.setShortDesc(courseDto.getShortDesc());
        course.setLongDesc(courseDto.getLongDesc());
        course.setPrice(courseDto.getPrice());
        course.setLive(courseDto.isLive());
        course.setDiscount(courseDto.getDiscount());
        course.setTitle(courseDto.getTitle());
        course.setCategoryList(courseDto.getCategoryList());
        course.setBanner(courseDto.getBanner());
        course.setCreatedDate(new Date());
        Course updatedCourse=courseRepo.save(course);
        return modelMapper.map(updatedCourse,CourseDto.class);
    }

    @Override
    public CourseDto saveBanner(MultipartFile file, String courseId) throws IOException {
        Course course=courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course not found!!"));
        String filePath=fileService.save(file, AppConstants.COURSE_BANNER_UPLOAD_DIR,file.getOriginalFilename());
        course.setBanner(filePath);
        course.setBannerContentType(file.getContentType());
        Course savedCourse= courseRepo.save(course);
        return modelMapper.map(savedCourse,CourseDto.class);
    }

    @Override
    public ResourceContentType getCourseBannerById(String courseId) {
        Course course=courseRepo.findById(courseId).orElseThrow(()->new ResourceNotFoundException("Course not found !!"));
        String bannerPath=course.getBanner();
        Path path=Paths.get(bannerPath);
        Resource resource=new FileSystemResource(path);
        ResourceContentType resourceContentType=new ResourceContentType();
        resourceContentType.setResource(resource);
        resourceContentType.setContentType(course.getBannerContentType());
        return resourceContentType;
    }


}
