package com.bhaskar.store.management.services;

import com.bhaskar.store.management.dtos.CategoryDto;
import com.bhaskar.store.management.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    //create category
    CategoryDto createCategory(CategoryDto categoryDto);

    //update category
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

    //delete category
    void deleteCategory(String categoryId);

    //get all category
    PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize, String sortBy, String sortDir);

    //get category by name
    CategoryDto getCategoryByTitle(String name);

    //get category by Id
    CategoryDto getCategoryById(String categoryId);

    //search category
    List<CategoryDto> searchByTitle(String keyword);
}
