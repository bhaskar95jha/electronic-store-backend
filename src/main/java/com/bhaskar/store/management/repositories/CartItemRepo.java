package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

}
