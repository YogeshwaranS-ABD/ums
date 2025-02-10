package com.i2i.ums.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ContactValidator.class})
public @interface ValidContact {
    String message() default "{com.i2i.ums.annotations.ValidContact.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
