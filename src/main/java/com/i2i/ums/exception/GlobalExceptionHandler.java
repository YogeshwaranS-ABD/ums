package com.i2i.ums.exception;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.i2i.ums.dto.ErrorResponseDto;
import com.i2i.ums.utils.Util;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleTeamNotFoundException(TeamNotFoundException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(OperationFailedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleOperationFailedException(OperationFailedException e) {
        return new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String message = e.getLocalizedMessage();
        if (message.contains("members_username_key")) {
            message = "The Username already exists, Try with another!";
        } else if (message.contains("contacts_value_key")) {
            message = "The Contact already exists. Try with other.";
        }
        return new ErrorResponseDto(HttpStatus.CONFLICT.value(), message);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleUnAuthorizedException(UnAuthorizedException e) {
        log.warn("Cannot process the request without authentication");
        return new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleBadCredentialException(BadCredentialsException e) {
        log.warn("Attempt to login with wrong credentials");
        return new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        log.warn("Un-Authorized request attempt");
        return new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAccessDeniedException(AccessDeniedException e) {
        log.warn("Un-Authorized API-Request attempt");
        return new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponseDto handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("Request with Unsupported method");
        return new ErrorResponseDto(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidContactException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidContactException(InvalidContactException e) {
        log.warn("Cannot create a User with Invalid Contact format.");
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getLocalizedMessage();
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), message.substring(424, 483));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleOtherExceptions(Exception e) {
        e.printStackTrace();
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
