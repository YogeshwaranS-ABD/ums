package com.i2i.ums.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.ums.repository.ContactRepository;
import com.i2i.ums.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void removeContactByValue(String value) {
        contactRepository.deleteByValueIgnoreCase(value);
    }
}
