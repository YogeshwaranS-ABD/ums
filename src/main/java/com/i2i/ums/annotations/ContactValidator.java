package com.i2i.ums.annotations;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.util.ObjectUtils;

import com.i2i.ums.dto.ContactDto;
import com.i2i.ums.exception.InvalidContactException;

import static com.i2i.ums.utils.Util.EMAIL_PATTERN;
import static com.i2i.ums.utils.Util.MOBILE_PATTERN;

public class ContactValidator implements ConstraintValidator<ValidContact, ContactDto> {

    @Override
    public void initialize(ValidContact constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ContactDto contactDto, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(contactDto.getValue())) {
            ((ConstraintValidatorContextImpl) constraintValidatorContext)
                    .addMessageParameter("com.i2i.ums.annotations.ValidContact.message",
                            "No Contact Value.");
            return false;
        }
        if (contactDto.getMedium().equals("email") && !EMAIL_PATTERN.matcher(contactDto.getValue()).matches()) {
            ((ConstraintValidatorContextImpl) constraintValidatorContext)
                    .addMessageParameter("com.i2i.ums.annotations.ValidContact.message",
                    "Invalid Email. Please check the email.");
            return false;
        } else if (contactDto.getMedium().equals("mobile") && !MOBILE_PATTERN.matcher(contactDto.getValue()).matches()) {
            ((ConstraintValidatorContextImpl) constraintValidatorContext)
                    .addMessageParameter("com.i2i.ums.annotations.ValidContact.message",
                    "Invalid Mobile number. Mobile number should have 10 digits");
            return false;
        }
        return true;
    }
}
