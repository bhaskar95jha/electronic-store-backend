package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,String> {
    //search by title
    List<Product> findByTitleContaining(String keyword);
    List<Product> findByIsLiveTrue();
}
