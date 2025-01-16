package com.elearn.app.services;

import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CustomPageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    String startCategory();
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategory(String categoryId);
    CustomPageResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    String deleteCategory(String categoryId);
    List<CategoryDto> findByTitle(String titleKeyword);

    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

}
