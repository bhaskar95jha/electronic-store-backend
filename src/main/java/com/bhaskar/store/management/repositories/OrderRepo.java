package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.dtos.PageableResponse;
import com.bhaskar.store.management.entity.Order;
import com.bhaskar.store.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,String> {
    PageableResponse<Order> findByUser(User user);
}
