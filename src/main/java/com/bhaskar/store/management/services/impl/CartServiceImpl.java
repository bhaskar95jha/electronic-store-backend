package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.dtos.CartDto;
import com.bhaskar.store.management.dtos.ItemToCart;
import com.bhaskar.store.management.entity.Cart;
import com.bhaskar.store.management.entity.CartItem;
import com.bhaskar.store.management.entity.Product;
import com.bhaskar.store.management.entity.User;
import com.bhaskar.store.management.exceptions.ResourceNotFoundException;
import com.bhaskar.store.management.repositories.CartRepository;
import com.bhaskar.store.management.repositories.ProductRepo;
import com.bhaskar.store.management.repositories.UserRepo;
import com.bhaskar.store.management.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CartDto addItemToCart(String userId, ItemToCart request) {

        //fetch the data from ItemToCart
        String productId = request.getProductId();
        int quantity = request.getQuantity();

        //fetch the product from db
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+productId));
        //fetch the user from db
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with Id "+userId));

        //fetch the cart of particular user if cart is available already
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        }catch (NoSuchElementException ex){
            cart = Cart
                    .builder()
                    .cartId(UUID.randomUUID().toString())
                    .createdDate(new Date())
                    .user(user)
                    .build();
        }

        //perform cart operations
        List<CartItem> items = cart.getItems();

        //if product already present in cart then update the quantity and total price
        AtomicInteger flag = new AtomicInteger(0);
        flag.set(0);
        List<CartItem> updatedItem = items.stream().map(
                item -> {
                    if (item.getProduct().getProductId().equalsIgnoreCase(productId)) {
                        item.setQuantity(item.getProduct().getQuantity() + quantity);
                        item.setTotalPrice(item.getTotalPrice()+quantity*product.getSellPrice());
                        flag.set(1);
                    }
                    return item;
                }
        ).collect(Collectors.toList());
        cart.setItems(updatedItem);

        //if product is not in the cart
        if(flag.get() == 0){
            CartItem item = CartItem
                    .builder()
                    .product(product)
                    .quantity(quantity)
                    .cart(cart)
                    .totalPrice(quantity*product.getSellPrice())
                    .build();
            cart.getItems().add(item);
        }

        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

    }

    @Override
    public void clearCart(String userId) {

    }
}
