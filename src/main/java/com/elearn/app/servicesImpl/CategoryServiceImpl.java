package com.elearn.app.servicesImpl;

import com.elearn.app.dtos.CategoryDto;
import com.elearn.app.dtos.CustomPageResponse;
import com.elearn.app.entities.Category;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.CategoryRepo;
import com.elearn.app.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CategoryServiceImpl implements CategoryService {

    CategoryRepo categoryRepo;
    ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo,ModelMapper modelMapper){
        this.categoryRepo=categoryRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public String startCategory() {

        return "Category Service Started";
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String Id= UUID.randomUUID().toString();
        categoryDto.setId(Id);
        categoryDto.setAddedDate(new Date());
        Category category=this.modelMapper.map(categoryDto,Category.class);
        categoryRepo.save(category);
        return categoryDto;
    }

    @Override
    public CategoryDto getCategory(String categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CustomPageResponse getAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
      //  Sort sort= Sort.by(sortBy);
        Sort sort=Sort.by(sortBy);
        PageRequest pageRequest=PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> categoryPage=categoryRepo.findAll(pageRequest);
        List<Category> all=categoryPage.getContent();
        List<CategoryDto> categoryDtoList=all.stream().map(cat->modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
        CustomPageResponse<CategoryDto> customPageResponse=new CustomPageResponse<CategoryDto>();
        customPageResponse.setContent(categoryDtoList);
        customPageResponse.setLast(categoryPage.isLast());
        customPageResponse.setTotalElements(categoryPage.getTotalElements());
        customPageResponse.setTotalPages(categoryPage.getTotalPages());
        customPageResponse.setPageSize(categoryPage.getSize());
        customPageResponse.setPageNumber(pageNumber);
        return customPageResponse;
    }

    @Override
    public String deleteCategory(String categoryId) {
       Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
       categoryRepo.delete(category);
       return  "Category with categoryID "+categoryId+" is deleted";
    }

    @Override
    public List<CategoryDto> findByTitle(String titleKeyword) {
        Optional<Category> category=categoryRepo.findByTitle(titleKeyword);
        return category.stream().map(cat->modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        category.setAddedDate(new Date());
        category.setDesc(categoryDto.getDesc());
        category.setTitle(categoryDto.getTitle());
        categoryRepo.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }
}
