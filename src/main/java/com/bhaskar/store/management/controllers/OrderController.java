package com.bhaskar.store.management.controllers;

import com.bhaskar.store.management.dtos.ApiResponseMessage;
import com.bhaskar.store.management.dtos.OrderDto;
import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/user/{userId}/cart/{cartId}")
    public ResponseEntity<OrderDto> createOrder(
            @Valid
            @RequestBody OrderDto orderDto,
            @PathVariable String userId,
            @PathVariable String cartId
            ){
        OrderDto order = orderService.createOrder(orderDto, userId, cartId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);

        ApiResponseMessage responseMessage = ApiResponseMessage
                .builder()
                .message("Order removed successfully")
                .isSuccess(true)
                .status(HttpStatus.OK)
                .build();

        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(
            @RequestBody OrderDto orderDto,
            @PathVariable String orderId
    ){
        OrderDto order = orderService.updateOrder(orderDto,orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PageableResponse<OrderDto>> getOrderByUser(
            @PathVariable String userId,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "orderDate") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        PageableResponse<OrderDto> orderByUser = orderService.getOrderByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orderByUser,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrder(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "orderDate") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc") String sortDir
    ){
        PageableResponse<OrderDto> orderByUser = orderService.getAllOrder(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orderByUser,HttpStatus.OK);
    }


}
