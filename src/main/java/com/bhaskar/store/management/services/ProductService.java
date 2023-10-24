package com.bhaskar.store.management.services;

import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    //create product
    ProductDto createProduct(ProductDto productDto);

    //update product
    ProductDto updateProduct(ProductDto productDto,String productId);

    //delete product
    void deleteProduct(String productId);

    //get single product
    ProductDto getProductById(String productId);

    //get all product
    PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all : live product
    PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search by title
    PageableResponse<ProductDto> searchByTitle(String keyword,int pageNumber, int pageSize, String sortBy, String sortDir);

    //create product with category_id
    ProductDto createWithCategory(ProductDto productDto, String categoryId);

}
