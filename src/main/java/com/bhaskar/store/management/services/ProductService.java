package com.bhaskar.store.management.services;

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
    List<ProductDto> getAllProduct();

    //get all : live product
    List<ProductDto> getAllLiveProduct();

    //search by title
    List<ProductDto> searchByTitle(String keyword);

}
