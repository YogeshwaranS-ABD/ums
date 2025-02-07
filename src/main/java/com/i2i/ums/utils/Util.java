package com.i2i.ums.utils;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {
    private static final ModelMapper modelMapper = new ModelMapper();
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

    public static ModelMapper getModelMapper() {
//        modelMapper.typeMap(User.class, UserDto.class)
//                .addMappings(mapper -> {
//                    mapper.using().map(src -> src.getRoles().stream().map(Role::getName).toList(), UserDto::setRoles);
//                });
        return modelMapper;
    }

    public static BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
