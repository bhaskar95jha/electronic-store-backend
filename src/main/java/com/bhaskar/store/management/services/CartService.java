package com.bhaskar.store.management.services;

import com.bhaskar.store.management.dtos.CartDto;
import com.bhaskar.store.management.dtos.ItemToCart;

public interface CartService {

    //add item to cart
    //TODO : if cart is not avilable for user create the cart and then add
    //TODO : if cart is available then just add the item to cart
    CartDto addItemToCart(String userId, ItemToCart request);

    //remove item from cart
    void removeItemFromCart(int cartItem);

    //remove all item from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);

}
