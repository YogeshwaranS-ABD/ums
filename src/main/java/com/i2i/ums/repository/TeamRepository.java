package com.i2i.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ums.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    Team findByNameIgnoreCase(String name);
    boolean existsByName(String name);
}
