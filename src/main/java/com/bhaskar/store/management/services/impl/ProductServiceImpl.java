package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.dtos.ProductDto;
import com.bhaskar.store.management.entity.Product;
import com.bhaskar.store.management.exceptions.ResourceNotFoundException;
import com.bhaskar.store.management.repositories.ProductRepo;
import com.bhaskar.store.management.services.ProductService;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);

        Product product = mapper.map(productDto,Product.class);
        Product savedProduct = productRepo.save(product);
        ProductDto saverDto = mapper.map(savedProduct,ProductDto.class);

        return saverDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        //fetch the product by id
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not available with id "+productId));

        //set the updated value to the product
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setMrp(productDto.getMrp());
        product.setSellPrice(productDto.getSellPrice());
        product.setQuantity(productDto.getQuantity());
        product.setColor(productDto.getColor());
        product.setAddedDate(productDto.getAddedDate());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImage(productDto.getProductImage());

        //save the updated product with id
        Product updatedProduct = productRepo.save(product);

        ProductDto updatedDto = mapper.map(updatedProduct,ProductDto.class);

        return updatedDto;

    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not available with id "+productId));
        productRepo.delete(product);
    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not available with id "+productId));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pages = productRepo.findAll(pageable);
        PageableResponse<ProductDto> pageableResponse = Util.getPageableResponse(pages, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pages = productRepo.findByIsLiveTrue(pageable);
        PageableResponse<ProductDto> pageableResponse = Util.getPageableResponse(pages, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String keyword,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> pages = productRepo.findByTitleContaining(keyword,pageable);
        PageableResponse<ProductDto> pageableResponse = Util.getPageableResponse(pages, ProductDto.class);
        return pageableResponse;
    }
}
