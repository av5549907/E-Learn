package com.elearn.app.controllers;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.CustomMessage;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import static com.elearn.app.Config.AppConstants.DEFAULT_PAGE_NUMBER;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/start")
    ResponseEntity<String> startCategoryService(){

        return new ResponseEntity<>(categoryService.startCategory(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new  ResponseEntity<>(this.categoryService.createCategory(categoryDto),HttpStatus.CREATED);
    }
    @GetMapping("/{categoryId}")
    CategoryDto getCategory(@PathVariable String categoryId){

        return  this.categoryService.getCategory(categoryId);
    }

    @GetMapping
    ResponseEntity<CustomPageResponse<CategoryDto>> getAll(
            @RequestParam(value="pageNumber",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value="pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(value="sortDirection",required = false,defaultValue = AppConstants.SORT_DIRECTION) String sortDirection
    ){

        return new  ResponseEntity<>(this.categoryService.getAll(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<String>  deleteCategory(@PathVariable String categoryId){

        return new ResponseEntity<>(this.categoryService.deleteCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/title/{titleKeyword}")
    ResponseEntity<List<CategoryDto>> findByTitle(@PathVariable String titleKeyword){
       return  new ResponseEntity<>(this.categoryService.findByTitle(titleKeyword),HttpStatus.OK);
    }

    @PatchMapping("/{categoryId}")
    ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable String categoryId){
        return new ResponseEntity<>(this.categoryService.updateCategory(categoryDto,categoryId),HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}/course/{courseId}")
    ResponseEntity<CustomMessage> addCourseToCategory(@PathVariable String courseId,@PathVariable String categoryId){
        categoryService.addCourseToCategory(courseId,categoryId);
        CustomMessage customMessage=new CustomMessage();
        customMessage.setMessage("Category Updated !!");
        customMessage.setSuccess(true);
        return  new ResponseEntity<>(customMessage,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/courses")
    ResponseEntity<List<CourseDto>> getCoursesFromCategory(@PathVariable String categoryId){
        return  new ResponseEntity<>(categoryService.getCoursesFromCategory(categoryId),HttpStatus.OK);
    }





}
