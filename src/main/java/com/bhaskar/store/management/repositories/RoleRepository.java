package com.bhaskar.store.management.repositories;

import com.bhaskar.store.management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
}
