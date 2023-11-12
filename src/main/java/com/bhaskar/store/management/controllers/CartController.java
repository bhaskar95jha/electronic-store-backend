package com.bhaskar.store.management.controllers;

import com.bhaskar.store.management.dtos.ApiResponseMessage;
import com.bhaskar.store.management.dtos.CartDto;
import com.bhaskar.store.management.dtos.ItemToCart;
import com.bhaskar.store.management.services.CartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@Api(value = "CartController" , description = "This Rest API is related to perform Cart operations !!")
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(
            @PathVariable String userId,
            @RequestBody ItemToCart request){
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(
            @PathVariable  int itemId ){
        cartService.removeItemFromCart(itemId);
        ApiResponseMessage responseMessage = ApiResponseMessage
                .builder()
                .message("Item removed successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();

        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @PutMapping("/clear/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(
            @PathVariable String userId
    ){
        cartService.clearCart(userId);

        ApiResponseMessage responseMessage = ApiResponseMessage
                .builder()
                .message("Cart clear successfully")
                .status(HttpStatus.OK)
                .isSuccess(true)
                .build();

        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartByUser(
            @PathVariable String userId
    ){
        CartDto cartByUser = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }


}
