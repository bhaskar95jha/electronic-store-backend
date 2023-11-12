package com.bhaskar.store.management.controllers;

import com.bhaskar.store.management.dtos.*;
import com.bhaskar.store.management.entity.Product;
import com.bhaskar.store.management.services.FileService;
import com.bhaskar.store.management.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.profile.image.path}")
    private String imageUploadPath;

    //create product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto createdProductDto = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    //update products
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        ProductDto updatedProducts = productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(updatedProducts, HttpStatus.CREATED);
    }

    //delete products
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);

        ApiResponseMessage responseMessage = ApiResponseMessage
                .builder()
                .message("Product deleted successfully !")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();

        return ResponseEntity.ok(responseMessage);
    }

    //get product by id
    @GetMapping ("/{productId}")
    ResponseEntity<ProductDto> getProductById(@PathVariable String productId){
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    //get all product
    @GetMapping
    ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    //get all live product
    @GetMapping("/live/product")
    ResponseEntity<PageableResponse<ProductDto>> getAllLiveProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        PageableResponse<ProductDto> allProduct = productService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    //search product
    @GetMapping("/search/{keyword}")
    ResponseEntity<PageableResponse<ProductDto>> searchProducts(
            @PathVariable String keyword,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        PageableResponse<ProductDto> allProduct = productService.searchByTitle(keyword,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    //upload product image
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/prodImage/{productId}")
    ResponseEntity<ImageResponse> uploadImage(
            @PathVariable String productId,
            @RequestParam("prodImage") MultipartFile image) throws IOException {

        String imageName = fileService.uploadFile(image,imageUploadPath);

        ProductDto productDto = productService.getProductById(productId);
        productDto.setProductImage(imageName);
        productService.updateProduct(productDto,productId);

        ImageResponse response = ImageResponse
                .builder()
                .imageName(imageName)
                .message("Imahe uploaded successfully")
                .isSuccess(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //serve product image
    @GetMapping("/prodImage/{productId}")
    public void serveImage(
            @PathVariable String productId,
            HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.getProductById(productId);
        InputStream resource = fileService.getResource(imageUploadPath, productDto.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}
