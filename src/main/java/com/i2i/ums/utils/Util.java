package com.i2i.ums.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.groups.Default;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.i2i.ums.annotations.CustomCheckGroups;
import com.i2i.ums.dto.MemberDto;
import com.i2i.ums.exception.ConstrainViolationException;

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

    public static String rootPath = System.getProperty("user.dir") + File.separator + "Uploads";

    public static String writeFile(MultipartFile file) {
        String filePath;
        try (FileOutputStream outStream = new FileOutputStream(rootPath + File.separator + file.getOriginalFilename())) {
            outStream.write(file.getBytes());
            filePath = rootPath + File.separator + file.getOriginalFilename();
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Couldn't able to read the file");
        }
    }

    public static List<String> getMessages(Set<ConstraintViolation<MemberDto>> violation) {
        if (!violation.isEmpty()) {
            return violation
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();
        }
        return null;
    }

}
