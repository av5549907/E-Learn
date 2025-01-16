package com.elearn.app.controllers;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.services.CategoryService;
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

    @PostMapping("/create")
    ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return new  ResponseEntity<>(this.categoryService.createCategory(categoryDto),HttpStatus.CREATED);
    }
    @GetMapping("/category/{categoryId}")
    CategoryDto getCategory(@PathVariable String categoryId){

        return  this.categoryService.getCategory(categoryId);
    }

    @GetMapping("/")
    ResponseEntity<CustomPageResponse<CategoryDto>> getAll(
            @RequestParam(value="pageNumber",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value="pageSize",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy",required = false,defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(value="sortDirection",required = false,defaultValue = AppConstants.SORT_DIRECTION) String sortDirection
    ){

        return new  ResponseEntity<>(this.categoryService.getAll(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @DeleteMapping("/category/delete/{categoryId}")
    ResponseEntity<String>  deleteCategory(@PathVariable String categoryId){

        return new ResponseEntity<>(this.categoryService.deleteCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/category/title/{titleKeyword}")
    ResponseEntity<List<CategoryDto>> findByTitle(@PathVariable String titleKeyword){
       return  new ResponseEntity<>(this.categoryService.findByTitle(titleKeyword),HttpStatus.OK);
    }

    @PatchMapping("/category/update/{categoryId}")
    ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable String categoryId){
        return new ResponseEntity<>(this.categoryService.updateCategory(categoryDto,categoryId),HttpStatus.OK);
    }

}
