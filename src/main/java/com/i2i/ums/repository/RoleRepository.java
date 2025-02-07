package com.i2i.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ums.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByNameIgnoreCase(String name);
    void deleteByNameIgnoreCase(String name);
}
