package com.i2i.ums.dto;

import java.util.List;

public class ContactsUpdateDto {
    private String action;
    private List<ContactDto> contacts;

    public ContactsUpdateDto() {}

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
    }
}
