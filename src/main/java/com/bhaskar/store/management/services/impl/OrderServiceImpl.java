package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.dtos.OrderDto;
import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.dtos.ProductQuantity;
import com.bhaskar.store.management.entity.*;
import com.bhaskar.store.management.exceptions.BadApiRequest;
import com.bhaskar.store.management.exceptions.ResourceNotFoundException;
import com.bhaskar.store.management.repositories.CartRepository;
import com.bhaskar.store.management.repositories.OrderRepo;
import com.bhaskar.store.management.repositories.ProductRepo;
import com.bhaskar.store.management.repositories.UserRepo;
import com.bhaskar.store.management.services.OrderService;
import com.bhaskar.store.management.utility.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto, String userId,String cartId) {
        //fetch user
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found with id "+userId));

        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart is not found with id "+cartId));

        List<CartItem> items = cart.getItems();
        if(items.size()== 0){
            throw new BadApiRequest("cart doesn't have any items");
        }


        Order order = Order
                .builder()
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderDate(new Date())
                .deliveredDate(orderDto.getDeliveredDate())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .oredrId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicInteger totalAmount = new AtomicInteger(0);

        List<OrderItem> orderItems = items.stream().map(item -> {
            OrderItem  orderItem = OrderItem
                    .builder()
                    .orderItemId(UUID.randomUUID().toString())
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .order(order)
                    .build();

            totalAmount.set(totalAmount.get()+orderItem.getTotalPrice());

            return orderItem;

        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(totalAmount.get());


        List<ProductQuantity> products = orderItems.stream().map((item) -> {
            ProductQuantity productQuantity = ProductQuantity
                    .builder()
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .build();

            return productQuantity;
        }).collect(Collectors.toList());

        //update the quantity of product after order
        products.stream().map(productQuantity -> {
            int updatedQuantity = productQuantity.getProduct().getQuantity() - productQuantity.getQuantity();
            productQuantity.getProduct().setQuantity(updatedQuantity);
            productRepo.save(productQuantity.getProduct());
            return productQuantity;
        });


        //clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);

        //save order to database
        Order savedOrder = orderRepo.save(order);
        OrderDto savedOrderDto = mapper.map(savedOrder, OrderDto.class);


        return savedOrderDto;
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order is not found with id : " + orderId));
        orderRepo.delete(order);

    }

    @Override
    public PageableResponse<OrderDto> getOrderByUser(String userId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found with id " + userId));

        Page<Order> pages = orderRepo.findByUser(user, pageable);

        PageableResponse<OrderDto> pageableResponse = Util.getPageableResponse(pages, OrderDto.class);

        return pageableResponse;

    }

    @Override
    public PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Order> pages = orderRepo.findAll(pageable);

        PageableResponse<OrderDto> pageableResponse = Util.getPageableResponse(pages, OrderDto.class);

        return pageableResponse;
    }
}
