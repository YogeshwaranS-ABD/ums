package com.i2i.ums.mapper;

import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.AbstractBeanField;

public class RoleConverter extends AbstractBeanField<List<String>, String> {

    @Override
    protected List<String> convert(String s) {
        String[] roles = s.split("\\|");
        return Arrays.stream(roles).toList();
    }
}
