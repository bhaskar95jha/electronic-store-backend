package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.dtos.CategoryDto;
import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.exceptions.ResourceNotFoundException;
import com.bhaskar.store.management.models.Category;
import com.bhaskar.store.management.repositories.CategoryRepo;
import com.bhaskar.store.management.services.CategoryService;
import com.bhaskar.store.management.utility.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDto,Category.class);
        Category saveCategory = categoryRepo.save(category);
        CategoryDto savedDto = modelMapper.map(saveCategory,CategoryDto.class);

        return savedDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id"+categoryId));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCategoryImage(categoryDto.getCategoryImage());

        Category updatedCategory = categoryRepo.save(category);
        CategoryDto updatedCategoryDto = modelMapper.map(updatedCategory,CategoryDto.class);

        return updatedCategoryDto;
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id"+categoryId));
        categoryRepo.delete(category);

    }


    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Category> pages = categoryRepo.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = Util.getPageableResponse(pages,CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto getCategoryByTitle(String title) {
        Category category = categoryRepo.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("Category not found with title : "+title));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id"+categoryId));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> searchByTitle(String keyword) {
        List<Category> categoryList = categoryRepo.findByTitleContaining(keyword).orElseThrow(() -> new ResourceNotFoundException("category does not find with this title"+keyword));
        List<CategoryDto> categoryDtoList = categoryList.stream().map((category) -> modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtoList;
    }
}
