package com.bhaskar.store.management.services.impl;

import com.bhaskar.store.management.dtos.CartDto;
import com.bhaskar.store.management.dtos.ItemToCart;
import com.bhaskar.store.management.entity.Cart;
import com.bhaskar.store.management.entity.CartItem;
import com.bhaskar.store.management.entity.Product;
import com.bhaskar.store.management.entity.User;
import com.bhaskar.store.management.exceptions.BadApiRequest;
import com.bhaskar.store.management.exceptions.ResourceNotFoundException;
import com.bhaskar.store.management.repositories.CartItemRepo;
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

    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public CartDto addItemToCart(String userId, ItemToCart request) {

        //fetch the data from ItemToCart
        String productId = request.getProductId();
        int quantity = request.getQuantity();
        if(quantity <0  ){
            throw  new BadApiRequest("Requested quantity is not valid ");
        }

        //fetch the product from db
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+productId));
        //fetch the user from db
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with Id "+userId));

        //fetch the cart of particular user if cart is available already
        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        }catch (NoSuchElementException ex){
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedDate(new Date());
            cart.setUser(user);
        }

        //perform cart operations

        AtomicInteger flag = new AtomicInteger(0);
        List<CartItem> items = cart.getItems();
        //if product already present in cart then update the quantity and total price

        items = items.stream().map(item -> {
                    if (item.getProduct().getProductId().equalsIgnoreCase(productId)) {
                        item.setQuantity(quantity);
                        item.setTotalPrice(quantity * product.getSellPrice());
                        flag.set(1);
                    }
                    return item;
                }
        ).collect(Collectors.toList());
        //cart.setItems(updatedItem);

        //if product is not in the cart
        if(flag.get() == 0){
            CartItem item = CartItem
                    .builder()
                    .product(product)
                    .quantity(quantity)
                    .cart(cart)
                    .totalPrice(quantity*product.getSellPrice())
                    .build();

            List<CartItem> cartItems = cart.getItems();
            cartItems.add(item);
        }


        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(int cartItemId) {
        CartItem cartItem = cartItemRepo.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cartitem not found with id "+cartItemId));
        cartItemRepo.delete(cartItem);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found with id "+userId));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart is not found for this user "+user));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found with id "+userId));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart is not found for this user "+user));
        return mapper.map(cart,CartDto.class);
    }
}
