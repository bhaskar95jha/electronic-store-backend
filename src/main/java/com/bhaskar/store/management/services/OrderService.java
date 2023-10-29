package com.bhaskar.store.management.services;

import com.bhaskar.store.management.dtos.OrderDto;
import com.bhaskar.store.management.dtos.PageableResponse;

public interface OrderService {
    //create order
    OrderDto createOrder(OrderDto orderDto,String userId,String cartId);

    //remove order
    void removeOrder(String orderId);

    //get order of user
    PageableResponse<OrderDto> getOrderByUser(String userId,int pageNumber,int pageSize,String sortBy, String sortDir);

    //get orders
    PageableResponse<OrderDto> getAllOrder(int pageNumber,int pageSize,String sortBy, String sortDir);

    OrderDto updateOrder(OrderDto orderDto, String orderId);

    //other api related to user
}
