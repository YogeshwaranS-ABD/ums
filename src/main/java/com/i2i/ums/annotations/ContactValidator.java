package com.i2i.ums.annotations;

import java.util.List;
import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.i2i.ums.dto.ContactDto;
import com.i2i.ums.dto.MemberDto;
import com.i2i.ums.exception.InvalidContactException;

@Aspect
@Component
public class ContactValidator{

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");

    @Pointcut("execution(* com.i2i.ums.controller.MemberController.createUser(..))")
    public void createUser() {}

    @Before("createUser()")
    public void validateContactsOfUser(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MemberDto dto = (MemberDto) args[0];
        List<ContactDto> contacts = dto.getContacts();
        for(ContactDto contact: contacts) {
            String medium = contact.getMedium().toLowerCase();
            if (medium.equals("email") && !EMAIL_PATTERN.matcher(contact.getValue()).matches()) {
                throw new InvalidContactException("Invalid Email. Check the format of the email.");
            } else if (medium.equals("mobile") && !MOBILE_PATTERN.matcher(contact.getValue()).matches()) {
                throw new InvalidContactException("Invalid Mobile Number. Mobile Number Should be 10-digits.");
            }
        }
    }


}
