package com.i2i.ums.utils;

import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {
    private static final ModelMapper modelMapper = new ModelMapper();
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    public static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    public static BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
