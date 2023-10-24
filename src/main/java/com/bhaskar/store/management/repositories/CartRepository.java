package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.Cart;
import com.bhaskar.store.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);
}
