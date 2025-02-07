package com.i2i.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.i2i.ums.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
    Contact findByValueIgnoreCase(String value);
    void deleteByValueIgnoreCase(String value);
}
