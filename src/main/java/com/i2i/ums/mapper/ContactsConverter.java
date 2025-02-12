package com.i2i.ums.mapper;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import com.i2i.ums.dto.ContactDto;

public class ContactsConverter extends AbstractBeanField<List<com.i2i.ums.dto.ContactDto>, String> {
    @Override
    protected List<ContactDto> convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        List<ContactDto> dtoList = new ArrayList<>();
        String[] contacts = s.split(",");
        for(String contact: contacts) {
            String[] details = contact.split("\\|");
            ContactDto dto = new ContactDto();
            dto.setMedium(details[0]);
            dto.setUseType(details[1]);
            if (details.length != 3) {
                dto.setValue(null);
            } else {
                dto.setValue(details[2]);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
}
