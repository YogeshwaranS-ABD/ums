package com.i2i.ums.mapper;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import com.i2i.ums.dto.AddressDto;

public class AddressConverter extends AbstractBeanField<AddressDto, String> {
    @Override
    protected AddressDto convert(String address) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        AddressDto dto = new AddressDto();
        String[] parts = address.split("\\|");
        dto.setDoorNo(parts[0]);
        dto.setStreet(parts[1]);
        dto.setArea(parts[2]);
        dto.setCity(parts[3]);
        dto.setPinCode(Integer.parseInt(parts[4]));
        return dto;
    }
}
