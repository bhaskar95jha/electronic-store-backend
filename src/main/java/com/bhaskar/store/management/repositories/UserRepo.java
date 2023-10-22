package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email,String password);
    Optional<User> findByName(String name);
    Optional<List<User>> findByGender(String gender);
    Optional<List<User>> findByNameContaining(String keyword);

    Optional<List<User>> findByGenderContaining(String gender);

}
