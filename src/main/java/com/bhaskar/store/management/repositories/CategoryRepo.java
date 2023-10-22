package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,String> {
    Optional<Category> findByTitle(String title);

    Optional<List<Category>> findByTitleContaining(String keyword);

}
