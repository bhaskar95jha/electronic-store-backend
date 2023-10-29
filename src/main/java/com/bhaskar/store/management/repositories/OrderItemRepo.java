package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem,String> {
}
