package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.Order;
import com.bhaskar.store.management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,String> {
    Page<Order> findByUser(User user, Pageable pageable);
}
