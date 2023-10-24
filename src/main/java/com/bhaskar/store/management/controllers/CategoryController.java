package com.bhaskar.store.management.controllers;

import com.bhaskar.store.management.dtos.*;
import com.bhaskar.store.management.services.CategoryService;
import com.bhaskar.store.management.services.FileService;
import com.bhaskar.store.management.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> upadteCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable String categoryId){
        CategoryDto updateCategoryDto = categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updateCategoryDto,HttpStatus.OK);
    }

    @DeleteMapping ("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage
                .builder()
                .message("Category deleted Successfully !")
                .isSuccess(Boolean.TRUE)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId){
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("title/{categoryTitle}")
    public ResponseEntity<CategoryDto> getCategoryByTitle(@PathVariable String categoryTitle){
        CategoryDto categoryDto = categoryService.getCategoryByTitle(categoryTitle);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchByTitle(@PathVariable String keyword){
        List<CategoryDto> categoryDtos = categoryService.searchByTitle(keyword);
        return ResponseEntity.ok(categoryDtos);
    }

    @PostMapping("/catimage/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(
            @RequestParam("categoryImage") MultipartFile image,
            @PathVariable String categoryId) throws IOException {

        String imageName = fileService.uploadFile(image,imageUploadPath);

        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        categoryDto.setCategoryImage(imageName);
        categoryService.updateCategory(categoryDto,categoryId);

        ImageResponse imageResponse = ImageResponse
                .builder()
                .imageName(imageName)
                .message("Image Created")
                .status(HttpStatus.CREATED)
                .isSuccess(Boolean.TRUE)
                .build();

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping("/catimage/{categoryId}")
    public void serveImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        InputStream resource = fileService.getResource(imageUploadPath, categoryDto.getCategoryImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    //create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable String categoryId,
            @RequestBody ProductDto productDto){
        ProductDto product = productService.createWithCategory(productDto, categoryId);

        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    //assign category to existing product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> assignCategoryToProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ){
        ProductDto productDto = productService.assignCategoryToExistingProduct(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //ge all products with same categories
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsWithSameCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        PageableResponse<ProductDto> pageableResponse = productService.getAllProductsWithSameCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

}
