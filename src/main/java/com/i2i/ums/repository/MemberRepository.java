package com.i2i.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ums.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
//    Member findByName(String name);
    Member findByUsernameIgnoreCase(String username);
}
