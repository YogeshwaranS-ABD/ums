package com.i2i.ums.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.i2i.ums.exception.OperationFailedException;
import com.i2i.ums.model.Contact;
import com.i2i.ums.repository.ContactRepository;
import com.i2i.ums.service.impl.ContactServiceImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {

    @InjectMocks
    private ContactServiceImpl contactServiceImpl;

    @Mock
    private ContactRepository contactRepository;

    private Contact contact;

    @BeforeEach
    public void setup() {
        contact = new Contact();
        contact.setUseType("Personal");
        contact.setMedium("Mobile");
        contact.setValue("9876543210");
    }

    @Test
    public void testRemoveContactByValue_validValue() {
        when(contactRepository.findByValueIgnoreCase("9876543210")).thenReturn(contact);
        assertDoesNotThrow(() -> contactServiceImpl.removeContactByValue("9876543210"));
    }

    @Test
    public void testRemoveContactByValue_invalidValue() {
        when(contactRepository.findByValueIgnoreCase("9876543210")).thenReturn(null);
        assertThrows(OperationFailedException.class, () -> contactServiceImpl.removeContactByValue("9876543210"));
    }

}
