package com.example.myblogproject.repository;

import com.example.myblogproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findRoleByNameIgnoreCase(String roleName);
    }
