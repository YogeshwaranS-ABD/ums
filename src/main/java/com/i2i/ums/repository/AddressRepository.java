package com.i2i.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ums.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
