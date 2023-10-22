package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,String> {
    //search by title
    Page<Product> findByTitleContaining(String keyword,Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);
}
