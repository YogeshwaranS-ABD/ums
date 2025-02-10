package com.i2i.ums.annotations;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.util.ObjectUtils;

import com.i2i.ums.dto.MemberDto;

public class CredentialsValidator implements ConstraintValidator<ValidCredentials, MemberDto> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{4,}$");

    @Override
    public void initialize(ValidCredentials constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MemberDto member, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(member)) {
            return true;
        }
        boolean result = member.getContacts().stream().anyMatch(contact -> contact.getValue().equals(member.getUsername()));
        if (!result) {
            ((ConstraintValidatorContextImpl) constraintValidatorContext)
                    .addMessageParameter("com.i2i.ums.annotations.ValidCredentials.message",
                            "The given username does not follow the defined constraints.");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(member.getPassword()).matches()) {
            ((ConstraintValidatorContextImpl) constraintValidatorContext)
                    .addMessageParameter("com.i2i.ums.annotations.ValidCredentials.message",
                            "The given password does not follow the defined constraints.");
            return false;
        }
        return true;
    }
}
